function compressedData = compress(originalData,range)
% originalData financial data structure
% range indexies to be compressed
filter = 1; % Always apply filter M = M-Mprev

import java.util.HashMap
%% eliminate already comressed symbols
rangeSize = length(range);
i = 1;
while(i <= rangeSize)
    if (originalData.compressionMap.containsKey(range(i))) % Check if compressed
        range(i) = []; % Eliminate if already compressed!
        rangeSize = rangeSize-1;
    else
        i = i + 1;
    end
end

%% calculate dimentions
len = 0;
for i = range
    len = len + length(originalData.marketData(i).time);
end
lastSeriesIdx=1;

%% copy data for compression 
% number of new comressed data chunk
chunkNumber = length(originalData.compressedData)+1;
% market data for compression
marketTimeSeries = NaN(len,length(originalData.marketData(1).fields)+1);
for i = range
    % copy time and delete original
    seriesLen = originalData.marketData(i).seriesLen;
    marketTimeSeries(lastSeriesIdx:lastSeriesIdx+seriesLen-1,1) = originalData.marketData(i).time;
    originalData.marketData(i).time = [];
    % copy data dand delete original
    marketTimeSeries(lastSeriesIdx:lastSeriesIdx+seriesLen-1,2:end) = originalData.marketData(i).data;
    originalData.marketData(i).data = [];
    lastSeriesIdx = lastSeriesIdx + seriesLen;
    % mark as copied
    originalData.compressionMap.put(i, [chunkNumber range]); 
end

%% Apply filter if needed
if (filter == 1),
    marketTimeSeries(2:end,:) = marketTimeSeries(2:end,:) - marketTimeSeries(1:end-1,:);
end

%% comress
chunk = dzip(marketTimeSeries);
clear marketTimeSeries;

%% return updted structure
compressedData = originalData;
compressedData.compressedData{chunkNumber} = chunk;