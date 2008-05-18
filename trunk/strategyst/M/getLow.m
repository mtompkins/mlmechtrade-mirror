function low = getLow(data, index)
% Returns low series
% Note: Lookup for position of "Low" of index may be done. Just use fixed
% position for simplicity
low = data.marketData(index).data(:,4);
