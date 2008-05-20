function exportTimeSeries2cvs(data, dateTimeFormat)
% Exports data to CSV files 
% yyyymmddHHMM data format, or specified via parameter dateTimeFormat
% Note: default version is more efficient
for i = 1:size(data.marketData,2)
    symbol = data.marketData(i);
    fileName = strcat(symbol.symbol,'.CSV');
    fid = fopen(fileName,'wt');
    if (nargin == 1)
        data2export = [str2num(datestr(symbol.time,'yyyymmddHHMM')) symbol.data];
        fprintf(fid,'%12.0f,%0.4f,%0.4f,%0.4f,%0.4f,%0.0f\n', data2export');
    else
        if (nargin == 2)
            for j = 1:size(data.marketData.data,1)
                fprintf(fid, '%s,', datestr(symbol.time(j),dateTimeFormat));
                fprintf(fid,'%0.4f,%0.4f,%0.4f,%0.4f,%0.0f', symbol.data(j,:));
                fprintf(fid,'\n');
            end % for j
        end 
    end
    fclose(fid);
end % for i