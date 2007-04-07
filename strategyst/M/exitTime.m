function exitSignals = exitTime(data, enterSignals, timeToHold)
% data - data in Strategist format
% enterSignals - enter signals in Strategists format
% timeToHold

%% Prepare resulting structure
exitSignals = {};

%% Iterate all symbols
for i = 1:size(data,2)
    exitSignals{i} = enterSignals{i} + timeToHold;
end