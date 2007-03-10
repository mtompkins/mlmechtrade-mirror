function conf=TsSystemManager(orderFactory,tradeFilter,portfolioManager,moneyManager,closeStrategy,etc)
%mt_newconf Create a trading system configuration.
%
%  Syntax
%
%    conf = mt_newconf(orderFactory,tradeFilter,portfolioManager,moneyManager,closeStrategy,etc)
%
%  Description
%
%    Trading system configuration
%    for trading system testing and execution
%
%    mt_newconf(system, orderFactory, tradeFilter, moneyManager, closeStrategy, prototype) takes these arguments,
%      orderFactory - Order Factory ('test' - for testing and 'eval' - for order processing)
%      tradeFilter - Trading filter (function name invoked before placing order)
%    and returns a trading system configuration.
%

conf.orderFactory = orderFactory;
conf.tradeFilter = tradeFilter;
conf.portfolioManager = portfolioManager;
conf.moneyManager = moneyManager;
conf.closeStrategy = closeStrategy;
