function profitLoss = equalPositionsProfitLoss(data, enterSignals, exitSignals, lots, slipage)
profitLoss = 0;
% Iterate all symbols
for i = 1:size(data,2)
    % close data
    close = data(i).data(:,4);
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
        -sum(close(enterSer(enterIdx))))/slipage*lots ... % expenses
        -2*lots*size(exitIdx,1); % 2 USD for minimum as commisions for 1 LOT
end