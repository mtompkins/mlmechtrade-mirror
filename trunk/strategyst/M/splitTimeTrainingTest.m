function [training,test] = splitTimeTrainingTest(data, continuesTicks, probability)
resIdx = 0;
training.compressionMap = java.util.HashMap();
training.compressedData = {};
test.compressionMap = java.util.HashMap();
test.compressedData = {};
for i = 1:size(data.marketData,2)
    symbol = data.marketData(i);
    randInterval = rand(ceil(symbol.seriesLen/continuesTicks),1) >= probability;
    s1 = find(randInterval == 0) * continuesTicks;
    s2 = find(randInterval == 1) * continuesTicks;
    clear randInterval;
    trainSetIdx = NaN(length(s1)*continuesTicks,1);
    testSetIdx = NaN(length(s2)*continuesTicks,1);
    for j = 1:size(s1,1)
        trainSetIdx(1+(j-1)*continuesTicks:j*continuesTicks,1) = s1(j)-continuesTicks+1:s1(j);
    end
    for j = 1:size(s2,1)
        testSetIdx(1+(j-1)*continuesTicks:j*continuesTicks,1) = s2(j)-continuesTicks+1:s2(j);    
    end
    clear s1 s2 j;
    if (size(trainSetIdx,1) > 0 && size(testSetIdx,1) > 0),    
        resIdx = resIdx + 1;
        trainSetIdx = trainSetIdx(find(trainSetIdx <= symbol.seriesLen));
        testSetIdx = testSetIdx(find(testSetIdx <= symbol.seriesLen));
    % Traingin Set 
        training.marketData(i).symbol = symbol.symbol;
        training.marketData(i).seriesLen = length(trainSetIdx);
        training.marketData(i).fields = symbol.fields;
        training.marketData(i).time = symbol.time(trainSetIdx,:);
        training.marketData(i).data = symbol.data(trainSetIdx,:);
        clear trainSetIdx;
    % Test Set    
        test.marketData(i).symbol = symbol.symbol;
        test.marketData(i).seriesLen = length(testSetIdx);
        test.marketData(i).fields = symbol.fields;
        test.marketData(i).time = symbol.time(testSetIdx,:);
        test.marketData(i).data = symbol.data(testSetIdx,:);
    end
end