function history = tradingSimulation(tradingHistory, tradingConfig, lEnterSignals, lExitSignals, sEnterSignals, sExitSignals, slipage)
%% mandatory parameters
if nargin < 1
    error('Initial trading history is mandatory parameter!');
end

%% back testing parameters
if nargin < 2
    tradingConfig = tradingConfig();
end

%% Create data structures for storing simulation state
% The amount for each symbol
tCurrntEquity = tradingHistory.currentEquity;

%% find max bar number
%%for symbolNr = 1:size(hist.marketData.marketData,2)
%%    symbol = hist.marketData.marketData(symbolNr);
%%end



