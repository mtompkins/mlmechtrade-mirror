function low = getLow(data, index)
% Returns low series

rez = getSymbolData(data, index);
low =  rez(:,4);
if (data.filter == 1)
    low(2:end) = low(2:end) + low(1);
end