function newHistory = tradingSimulation(initHistory, tradingConfig, lEnterSignals, lExitSignals, sEnterSignals, sExitSignals, slipage)
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
tCurrentEquity = initHistory.currentEquity;
% Data
data = initHistory.data;
% Transform enter signals
tSignals = transforSignals(data, lEnterSignals, lExitSignals);

%% Iterate signals
for i = 1:size(tSignals,1)
    bar = tSignals(i,2);
    symbolNr = tSignals(i,3);
    isEnter =  tSignals(i,4);
    close = getClose(data, symbolNr);
    tCurrntEquity = tCurrentEquity - sign(isEnter-0.5)*close(bar);
end

%% Update returning structure
newHistory.data = initHistory.data;
newHistory.currntEquity = tCurrntEquity;
%newHistory.openPositions = tOpenPositions;
 
    
    
%% Transform trading signals "symbol => enterBar and symbol => exitBar" to "ordered matrix
% [:,[time,symbol,bar,isEnter]]"
function tsignals = transforSignals(data, signals1, signals0)
% Calculate dimensins of returning structure
dim = 0;
for i = 1:size(signals1,2)
    dim = dim + size(signals1{i},1) + size(signals0{i},1);
end
%% Allocate memory
tsignals = zeros(dim, 4);
% Iterate and create matrix for storing: 
% (:,1)-time; (:,2)-symbol#;(:,3)-signal bar
for i = 1:size(signals1,2)
    % Enter 1
    % Store sygnals for symbol #i
    signalsForSymbol = signals1{i};
    % Store lenght of signal arrray
    sigLen = size(signalsForSymbol,1);
    % Time for symbol #i
    time = getTime(data,i); 
    tsignals(dim-sigLen+1:dim,1) = time(signalsForSymbol);
    tsignals(dim-sigLen+1:dim,2) = signalsForSymbol;
    tsignals(dim-sigLen+1:dim,3) = i;
    tsignals(dim-sigLen+1:dim,4) = 1;
    dim = dim-sigLen;
    % Exit 0
    % Store sygnals for symbol #i
    signalsForSymbol = signals0{i};
    % Store lenght of signal arrray
    sigLen = size(signalsForSymbol,1);
    % Time for symbol #i
    time = getTime(data,i); 
    tsignals(dim-sigLen+1:dim,1) = time(signalsForSymbol);
    tsignals(dim-sigLen+1:dim,2) = signalsForSymbol;
    tsignals(dim-sigLen+1:dim,3) = i;
    tsignals(dim-sigLen+1:dim,4) = 0;
    dim = dim-sigLen;
end
%% Sort result
tsignals = sort(tsignals);