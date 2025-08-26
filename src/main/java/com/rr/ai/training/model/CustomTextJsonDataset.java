package com.rr.ai.training.model;
import ai.djl.basicdataset.utils.TextData;
import ai.djl.training.dataset.RandomAccessDataset;
import ai.djl.ndarray.NDManager;
import ai.djl.training.dataset.Record;
import ai.djl.modality.nlp.preprocess.LowerCaseConvertor;
import ai.djl.modality.nlp.preprocess.PunctuationSeparator;
import ai.djl.modality.nlp.preprocess.SimpleTokenizer;
import ai.djl.modality.nlp.preprocess.TextProcessor;
import ai.djl.modality.nlp.preprocess.TextTruncator;
import ai.djl.modality.nlp.preprocess.TextTerminator;
import ai.djl.util.Pair;
import ai.djl.util.Progress;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
public final class CustomTextJsonDataset extends RandomAccessDataset {

        private List<String> sourceTexts;
        private List<String> targetJsons;
        private TextData sourceData;
        private TextData targetData;

        public CustomTextJsonDataset(Builder builder) {
            super(builder);
            this.sourceTexts = builder.sourceTexts;
            this.targetJsons = builder.targetJsons;
            //this.sourceData = builder.sourceConfig;
            //this.targetData = builder.targetConfig;
        }

        @Override
        public Record get(NDManager manager, long index) throws IOException {
            // Get the raw text from the lists
            String sourceText = sourceTexts.get((int) index);
            String targetJson = targetJsons.get((int) index);

            // Process the text using the configured pipelines
            /*List<String> processedSource = sourceData.preprocessText(Collections.singletonList(sourceText)).get(0);
            List<String> processedTarget = targetData.preprocessText(Collections.singletonList(targetJson)).get(0);

            // Create NDArrays from the processed text (this part is crucial for a dataset)
            // For now, we'll just return the processed text strings as a pair,
            // but in a real-world scenario, you would convert these into
            // embeddings or token IDs.
            return new Record(
                new Pair<>(processedSource, processedTarget),
                new Pair<>(processedTarget, Collections.emptyList()) // Placeholder, since the target is also the label in this case
            );*/
            return null;
        }

        @Override
        public void prepare(Progress progress) throws IOException {
            // Pre-process the entire dataset here
            // This is a more robust way to handle the preparation step
           // sourceData.preprocess(sourceTexts);
            //targetData.preprocess(targetJsons);
            // The actual data is now ready for use by the get() method
        }

        @Override
        protected long availableSize() {
            return sourceTexts.size();
        }

        public static final class Builder extends BaseBuilder<Builder> {
            List<String> sourceTexts;
            List<String> targetJsons;
            TextData.Configuration sourceConfig;
            TextData.Configuration targetConfig;

            public Builder() {
                // Default constructor
                setSampling(8, false); // Example default sampling
            }

            public Builder setSourceTexts(List<String> sourceTexts) {
                this.sourceTexts = sourceTexts;
                return this;
            }

            public Builder setTargetJsons(List<String> targetJsons) {
                this.targetJsons = targetJsons;
                return this;
            }

            public Builder setSourceConfiguration(TextData.Configuration sourceConfig) {
                this.sourceConfig = sourceConfig;
                return this;
            }

            public Builder setTargetConfiguration(TextData.Configuration targetConfig) {
                this.targetConfig = targetConfig;
                return this;
            }

            @Override
            protected Builder self() {
                return this;
            }

            public CustomTextJsonDataset build() {
                return new CustomTextJsonDataset(this);
            }

            public Builder setManager(NDManager manager) {
                return this;
            }
        }
    }