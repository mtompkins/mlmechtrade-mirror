function volume = getVolume(data, index)
% Returns Volume series
volume =  data.marketData(index).data(:,5);