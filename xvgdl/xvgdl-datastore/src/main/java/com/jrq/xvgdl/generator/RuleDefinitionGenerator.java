package com.jrq.xvgdl.generator;

import com.jrq.xvgdl.context.xml.GameDefinitionXMLMapper;
import com.jrq.xvgdl.generator.publisher.XvgdlExternalublisher;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.IntStream;

import static com.jrq.xvgdl.generator.utils.RuleDefinitionGeneratorUtils.generateGameDefinition;

@Slf4j
public class RuleDefinitionGenerator {

    private static final String OUPUT_PATH = "./gameRulesGeneratorStore.xml";
    private static final int DEFAULT_NUMBER_OF_RULES = 100_000;

    private static XvgdlExternalublisher publisher = null;
    private static int numberOfRules = DEFAULT_NUMBER_OF_RULES;

    public static void main(String[] args) {
        initializeNumberOfRules(args);

        initializePublisher(args);

        generateContextFileDataBase(numberOfRules);

        publish();
    }

    private static void generateContextFileDataBase(int numberOfRules) {
        long start = System.currentTimeMillis();
        GameDefinitionXMLMapper mapper = new GameDefinitionXMLMapper();
        mapper.storeXml(OUPUT_PATH, generateGameDefinition(numberOfRules));
        log.info("Generating " + numberOfRules + " process finished in " + (System.currentTimeMillis() - start) + " ms.");
    }

    private static void publish() {
        if (publisher != null) {
            publisher.publish(OUPUT_PATH);
        }
    }

    private static void initializePublisher(String[] args) {
        IntStream.range(0, args.length).forEach(index -> {
            try {
                if ("--publisher".equals(args[index])) {
                    publisher = (XvgdlExternalublisher)
                            Class.forName(args[index + 1]).getDeclaredConstructor().newInstance();
                }
            } catch (Exception e) {
                log.error("Error creating the external publisher.", e);
            }
        });
    }

    private static void initializeNumberOfRules(String[] args) {
        try {
            IntStream.range(0, args.length).forEach(index -> {
                if ("--numberOfRules".equals(args[index])) {
                    numberOfRules = Integer.parseInt(args[index + 1]);
                }
            });
        } catch (Exception e) {
            numberOfRules = DEFAULT_NUMBER_OF_RULES;
        }
    }

}
