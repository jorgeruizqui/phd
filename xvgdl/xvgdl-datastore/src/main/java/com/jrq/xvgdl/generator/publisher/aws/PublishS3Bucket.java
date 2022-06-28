package com.jrq.xvgdl.generator.publisher.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.transfer.Transfer;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.jrq.xvgdl.generator.publisher.XvgdlExternalublisher;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
@NoArgsConstructor
public class PublishS3Bucket implements XvgdlExternalublisher {

    private static final String BUCKET_NAME = "xvgdl_generator";
    private static final String KEY = "rules";

    public boolean publish(String filePathToPublish) {

        File f = new File(filePathToPublish);
        TransferManager xfer_mgr = TransferManagerBuilder.standard().build();
        try {
            Upload xfer = xfer_mgr.upload(BUCKET_NAME, KEY, f);
            waitForCompletion(xfer);
        } catch (Exception e) {
            log.error("Error publishing in AWS S3 Bucket: ", e);
            return false;
        }
        xfer_mgr.shutdownNow();
        return true;
    }

    public static void waitForCompletion(Transfer xFer) throws Exception {
        try {
            xFer.waitForCompletion();
        } catch (AmazonServiceException | InterruptedException e) {
            log.error("Amazon service error: " + e.getMessage());
            throw e;
        } catch (AmazonClientException e) {
            log.error("Amazon client error: " + e.getMessage());
            throw e;
        }
    }

}
