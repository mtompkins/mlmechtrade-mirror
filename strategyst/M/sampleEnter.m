function enterSignals = sampleEnter(data)
%% Time shift 
%% Parameters
reactionTime = 1; % Reaction time
volumeMin = 500;
priceMin = 1;

%% prepare results
enterSignals = {};

%% Iterate symbols
for i = 1:size(data.marketData,2)
    % retrieve close prices
    close = getClose(data,i);
    % retrieve volumes
    volume = getVolume(data,i);
    % Simple Moving Averange of Volume
    smaVolume = TA_SMA(volume,100);
    % Strategy specific
    smaLong = TA_SMA(close,200); 
    smaShort = TA_SMA(close,80);
    % shift one period before. For stupid cros below aproximation
    smaShortPrev = smaShort(1:end-reactionTime);
    smaShortPrev = [zeros(reactionTime,1); smaShortPrev];
    % Enter Rules
    enterSignals{i} = find( ...
        smaVolume > volumeMin & ...
        close > priceMin & ...
        smaShortPrev < smaLong & ...
        smaShort > smaLong ...
    )+1; % Put Orders one bar to future
end