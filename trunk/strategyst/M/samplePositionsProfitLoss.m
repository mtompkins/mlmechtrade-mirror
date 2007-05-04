function profitLoss = equalPositionsProfitLoss(data, enterSignals, exitSignals, lots, slipage)
profitLoss = 0;
% Iterate all symbols
for i = 1:size(data.marketData,2)
    % close data
    close = getClose(data, 2);
    % number of ticks
    ticksNbr = size(close,1);
    % enter bars
    enterSer = enterSignals{i};
    % exit bars
    exitSer = exitSignals{i};
    % allow only valid ranges
    enterIdx = find(enterSer <= ticksNbr);
    % allow only valid ranges
    exitIdx = find(exitSer <= ticksNbr);
    % estimate only openend and closed positons
    if (size(exitIdx,1) > 0)
        enterIdx = find(enterIdx < exitIdx(end));
    end

    % profict aproximation TODO: Improve
    % (incorrect asumption: each enter lot has corresponding exit lot)
    profitLoss = profitLoss +  ...
        sum(close(exitSer(exitIdx))*slipage*lots ...      % gain
        -sum(close(enterSer(enterIdx))))/slipage*lots; % expenses
end