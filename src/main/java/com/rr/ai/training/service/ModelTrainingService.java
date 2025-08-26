package com.rr.ai.training.service;

import ai.djl.Model;
import ai.djl.basicdataset.nlp.TextDataset;
import ai.djl.basicdataset.utils.TextData;
import ai.djl.modality.nlp.preprocess.*;
import ai.djl.ndarray.NDManager;
import ai.djl.training.DefaultTrainingConfig;
import ai.djl.training.Trainer;
import ai.djl.training.dataset.RandomAccessDataset;
import ai.djl.training.listener.TrainingListener;
import ai.djl.training.loss.Loss;
import ai.djl.training.optimizer.Adam;
import ai.djl.translate.TranslateException;
import com.fasterxml.jackson.core.JsonProcessingException;
import ai.djl.training.dataset.RandomAccessDataset;
import com.fasterxml.jackson.databind.ObjectMapper;
import ai.djl.training.EasyTrain;
import com.rr.ai.training.model.CustomTextJsonDataset;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class ModelTrainingService {

    public void prepareDataset() throws IOException, TranslateException {

        NDManager manager = NDManager.newBaseManager();


        // Load or prepare your text dataset here, mapped to desired JSON outputs
        RandomAccessDataset trainingSet = prepareTextJsonDataset(manager);

        // Define the model (e.g., LSTM or Transformer based)
        Model model = Model.newInstance("text-to-json");

        // Define training configuration
        DefaultTrainingConfig config = new DefaultTrainingConfig(Loss.softmaxCrossEntropyLoss())
                .optOptimizer(Adam.builder().build())
                .addTrainingListeners(TrainingListener.Defaults.logging());

        try (Trainer trainer = model.newTrainer(config)) {
            trainer.initialize(new ai.djl.ndarray.types.Shape(1, 100)); // Example input shape

            // Train loop
            for (int epoch = 0; epoch < 10; epoch++) {
                for (var batch : trainer.iterateDataset(trainingSet)) {
                    EasyTrain.trainBatch(trainer, batch);
                    trainer.step();
                    batch.close();
                }
                trainer.notifyListeners(listener -> listener.onEpoch(trainer));
            }
        }

        // Sample inference (prepare actual input accordingly)
        var input = manager.zeros(new ai.djl.ndarray.types.Shape(1, 100)); // example dummy input

        // Predict JSON output
        //var output = model.newPredictor().predict(input);
        var output = "";

        // Convert model output to JSON string (using Jackson)
        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = mapper.writeValueAsString(output);
    }

    private static RandomAccessDataset prepareTextJsonDataset( NDManager manager) throws TranslateException, IOException {
        // Text preprocessing pipeline for the input (source) text
        TextData.Configuration sourceConfig = new TextData.Configuration()
                .setTextProcessors(Arrays.asList(
                        new SimpleTokenizer(),
                        new LowerCaseConvertor(Locale.ENGLISH),
                        new PunctuationSeparator(),
                        new TextTruncator(50))); // truncates to max 50 tokens

        // For output JSON, treat as target text sequence with terminator on end
        TextData.Configuration targetConfig = new TextData.Configuration()
                .setTextProcessors(Arrays.asList(
                        new SimpleTokenizer(),
                        new LowerCaseConvertor(Locale.ENGLISH),
                        new PunctuationSeparator(),
                        new TextTruncator(100), // limit JSON tokens length
                        new TextTerminator()));

        // Create a custom dataset from text to JSON string pairs
        // Example pairs; in practice load from files or DB
        String[] inputTexts = {
                "What is the weather today?",
                "Tell me the time",
                "Play music by Beethoven"
        };

        String[] targetJsons = {
                "{\"weather\":\"sunny\",\"temperature\":\"25C\"}",
                "{\"time\":\"10:00AM\"}",
                "{\"action\":\"play_music\",\"artist\":\"Beethoven\"}"
        };

        // Use a concrete implementation of TextDataset.Builder, in this case,
        CustomTextJsonDataset dataset = new CustomTextJsonDataset.Builder()
                .setSourceTexts(Arrays.asList(inputTexts))
                .setTargetJsons(Arrays.asList(targetJsons))
                .setSourceConfiguration(sourceConfig)
                .setTargetConfiguration(targetConfig)
                .setManager(manager)
                .build();

        // Prepare dataset with progress bar (you can omit or customize)
        dataset.prepare();

        return dataset;
    }
}
