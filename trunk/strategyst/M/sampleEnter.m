function enterSignals = sampleEnter(data, enterTimeShift)
%% Time shift 
%% Parameters
% .....
volumeMin = 500;
priceMin = 1;

%% prepare results
enterSignals = {};

%% Iterate symbols
for i = 1:size(data,2)
    % retrieve symbol
    symbol = data(i);
    % retrieve close prices
    close = symbol.data(:,4);
    % retrieve volumes
    volume = symbol.data(:,5);
    % Simple Moving Averange of Volume
    smaVolume = TA_SMA(volume,20);
    % Strategy specific
    % .....
    % Enter Rules
    enterSignals{i} = find( ...
        smaVolume > volumeMin & ...
        close > priceMin % & .
        % Strategy specific constrains 
    )+enterTimeShift+1; % Put Orders one bar to future
end