function history = tradingHistory(data, openPositions, currentEquity, trades, marginFactor, fixedAmountMargin)
% Function tradingHistory combines trading history and account state from
% parameters :
%
% data - Market Data
% currentEquity - Current Equity. Default is 0
% openPositions - Open Trading postions. Default is {}
% trades - History of performed Trades. Default is {}
% marginFactor - The Margin ration 1:marginFactor. Default is 1:1
% fixedAmountMargin - The fixed amount margin. Default is 0

%% marketData
if nargin < 1
     error('marketData is mandatory parameter');
end
history.marketData = data;

%% openPositions
if nargin < 2
    openPositions = {};
end
history.openPositions = openPositions;

%% currentEquity
if nargin < 3
    currentEquity = 0;
end
history.currentEquity = currentEquity;

%% trades
if nargin < 4
    trades = {};
end
history.trades = trades;

%% marginFactor
if nargin < 5
    marginFactor = 1; % 1:1 Default
end
history.marginFactor = marginFactor;

%% fixedAmountMargin
if nargin < 6
    fixedAmountMargin = 0; % 0 Default
end
history.fixedAmountMargin = fixedAmountMargin;



