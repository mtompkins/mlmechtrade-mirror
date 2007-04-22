function exportTimeSeries2cvs(data)
% Exports data to CSV files 
% yyyymmddHHMM data format,
for i = 1:size(data.marketData,2)
    symbol = data.marketData(i);
    fileName = strcat(symbol.symbol,'.CSV');
    data2export = [str2num(datestr(symbol.time,'yyyymmddHHMM')) symbol.data];
    fid = fopen(fileName,'wt');
    fprintf(fid,'%12.0f,%0.4f,%0.4f,%0.4f,%0.4f,%0.0f\n', data2export');
    fclose(fid);
end