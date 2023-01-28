package com.github.ioridazo.fundanalyzer.patch.run;

import com.github.ioridazo.fundanalyzer.patch.domain.processor.PatchProcessor;
import com.github.ioridazo.fundanalyzer.patch.listener.PatchListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PatchCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LogManager.getLogger(PatchCommandLineRunner.class);

    private final PatchProcessor processor;
    private final PatchListener listener;

    public PatchCommandLineRunner(
            final PatchProcessor processor,
            final PatchListener listener) {
        this.processor = processor;
        this.listener = listener;
    }

    @Override
    public void run(final String... args) {
        log.info("command line runner start.");
        Arrays.stream(args).forEach(
                commandOptionsAsString -> {
                    switch (CommandOptions.fromValue(commandOptionsAsString)) {
                        case DOCUMENT_PERIOD -> {
                            processor.documentPeriod();
                            listener.documentPeriod();
                        }
                        case UPDATE_FINANCIAL_STATEMENT -> {
                            processor.updateFinancialStatement();
                            listener.updateFinancialStatement();
                        }
                        case UPDATE_ANALYSIS_RESULT -> {
                            processor.updateAnalysisResult();
                            listener.updateAnalysisResult();
                        }
                        default -> log.info("{} についてはの処理はありません。", commandOptionsAsString);
                    }
                }
        );
        log.info("command line runner finish.");
    }
}
