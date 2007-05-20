function eodData = intradayToEOD(data)
eodData.filter = 0;
eodData.compressionMap = java.util.HashMap();
for i = 1:size(data.marketData,2)
    symbol = data.marketData(i);
    eodData.marketData(i).symbol = symbol.symbol;
    eodData.marketData(i).fields = symbol.fields;
    % time, seriesLen, data Calculate
    if symbol.seriesLen == 0
        eodData.marketData(i).time = [];
        eodData.marketData(i).seriesLen = 0;        
        eodData.marketData(i).data = [];
    else
        eodIndex = 1;
        dates = ceil(symbol.time);
        date = dates(1);
        intradayLastIndex = 1;
        for j=2:size(dates, 1);
            if date ~= dates(j)
                % Date
                eodData.marketData(i).time(eodIndex,1) = date;
                % Open
                eodData.marketData(i).data(eodIndex,1) = symbol.data(intradayLastIndex,1);
                % High
                eodData.marketData(i).data(eodIndex,2) = max(symbol.data(intradayLastIndex:j-1,2));
                % Low
                eodData.marketData(i).data(eodIndex,3) = min(symbol.data(intradayLastIndex:j-1,3));
                % Close
                eodData.marketData(i).data(eodIndex,4) = symbol.data(j-1,4);
                % Volume
                eodData.marketData(i).data(eodIndex,5) = sum(symbol.data(intradayLastIndex:j-1,5));
                % Next day
                eodIndex = eodIndex + 1;
                intradayLastIndex = j;
                date = dates(j);
            end
        end
        % Last/First day
        j = size(dates, 1) + 1;
        % Date
        eodData.marketData(i).time(eodIndex,1) = date;
        % Open
        eodData.marketData(i).data(eodIndex,1) = symbol.data(intradayLastIndex,1);
        % High
        eodData.marketData(i).data(eodIndex,2) = max(symbol.data(intradayLastIndex:j-1,2));
        % Low
        eodData.marketData(i).data(eodIndex,3) = min(symbol.data(intradayLastIndex:j-1,3));
        % Close
        eodData.marketData(i).data(eodIndex,4) = symbol.data(j-1,4);
        % Volume
        eodData.marketData(i).data(eodIndex,5) = sum(symbol.data(intradayLastIndex:j-1,5));
        % Next day
    end
end
