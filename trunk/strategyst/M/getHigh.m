function high = getHigh(data, index)
% Returns Open series

rez = getSymbolData(data, index);
high =  rez(:,3);
if (data.filter == 1)
    high(2:end) = high(2:end) + high(1);
end
