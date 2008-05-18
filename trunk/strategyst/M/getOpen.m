function open = getOpen(data, index)
% Returns Open series
open =  data.marketData(index).data(:,2);