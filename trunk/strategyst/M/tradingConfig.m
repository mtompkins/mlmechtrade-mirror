function simConfig = tradingConfig(posSizing, options)
% Function for storing the configuration of back teting engine in single
% structure. Parameters:
% 
% posSizing - Position sizing confuration. Default is
% posSizing.percentInEquity = 0.15; posSizing.maximumRiskPct = NaN; posSizing.fixedSharePerContract = NaN
%
% options - The configuration of simulator. Default is
% options.maxOpenPositions = 10; options.limitPosSizeToPCTOfVolume = NaN;
% options.positionSizingLeeway = NaN

%% posSizing
if nargin < 2
    posSizing.percentInEquity = 0.15;
    posSizing.maximumRiskPct = NaN;
    posSizing.fixedSharePerContract = NaN;
end    
simConfig.posSizing = posSizing;

%% options
if nargin < 1
   options.maxOpenPositions = 10;
   options.limitPosSizeToPCTOfVolume = NaN;
   options.positionSizingLeeway = NaN;
end
simConfig.options = options;
