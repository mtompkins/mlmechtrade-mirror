function [eod1,eod2] = intersectEodDataByDateRange(eod1,eod2)
% eod1 - set of dat
% Assumption - number of symbols and order ordered quotes structure is
% identical. Only EOD data used by function. 
% 
% Side effect: if time <> 00:00 it is corrected to 00:00 during processing
% TODO: update for intraday usage

for i = 1:size(eod1.marketData,2),
    ts1 = floor(getTime(eod2,i));
    eod2.marketData(i).time = ts1;
    ts2 = floor(getTime(eod1,i));
    eod1.marketData(i).time = ts2;
    % common data interval
    timeStart = max(ts1(1),ts2(2));
    timeEnd = min(ts1(end),ts2(end));
    % datestr(timeStart)
    % datestr(timeEnd)
    clear ts1  ts2;
    % cut data
    eod2 = cutDateInterval(eod2,timeStart,timeEnd,i);
    eod1 = cutDateInterval(eod1,timeStart,timeEnd,i);    
end % for