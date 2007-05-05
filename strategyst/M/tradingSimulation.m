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
% Data
data = tradingHistory.marketData;
% Transform enter signals
enter = transforSignals(data, lEnterSignals);
% Transform exit signals
enter = transforSignals(data, lExitSignals);

%% Result
history = tradingHistory;


function tsignals = transforSignals(data, signals)
% Calculate dimensins of returning structure
dim = 0;
for i = 1:size(signals,2)
    dim = dim + size(signals{i},1);
end
% Allocate memory
tsignals = zeros(dim, 3);
for i = 1:size(signals)
    TODO;
end



