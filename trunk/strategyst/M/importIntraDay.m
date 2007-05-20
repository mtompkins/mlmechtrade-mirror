%% Main cycle reding data from files
function data = importIntraDay(fileMask,compressEnabled,dateFormat,timeFormat,hasHeaderRow)
%ImportIntraDay
% Imports data from FileMask in Trader Station or similar ASCII format.
% <SYMBOL>_<time scale>.csv
% Default fileMask '*.csv'
% compressEnabled = true
% Default dataFormat 'yyyymmdd'
% Default timeFormat 'HH:MM'
% hasHeaderRow = true and OHLCV order expected for missing header

%% Defaults
% File Mask
if nargin < 1
    fileMask = '*.csv';
end
% Default compression and filter parameters
if nargin < 2
    compressEnabled = true;
    data.filter = 1;
else
    data.filter = 0;
end  % Use compression by default
% Default data
if nargin < 3
    dateFormat = 'yyyymmdd';
end
% Default time    
if nargin < 4
    timeFormat = 'HH:MM';
end
% Default of hasHeaderRow
if nargin < 5
    hasHeaderRow = 1;
end

%% Prepare output structure
import java.util.HashMap;
data.compressionMap = java.util.HashMap();
data.compressedData = {};
data.marketData = struct();
createGlobalCashe();

%% List files in current directory
files = ls(fileMask);
for j = 1:size(files,1),
    % Import the file
    fileToRead = files(j,1:end);
    data.marketData(j).symbol = symbolFromFileName(fileToRead);
    fileSize = getFileSize(fileToRead);
    if fileSize == 0
        tFields = {'Open','High','Low','Close','Volume'};
        tTime = [];
        tData = [];        
    else
        fileData = importdata(fileToRead);
        tFields = removeTxtSeps(fileData.textdata(1,3:end));
        tTime = asciiToTimeStamp(fileData,dateFormat,timeFormat,hasHeaderRow);
        tData = fileData.data;        
    end
    data.marketData(j).fields = tFields; 
    data.marketData(j).time = tTime;
    data.marketData(j).seriesLen = length(tTime);
    data.marketData(j).data = tData;
    if (compressEnabled),
        data = compress(data, j);
    end % end if
end % end for

%% helper functios
function symbol = symbolFromFileName(fileName)
% Retrieve symbol from TS export file name
expr = '(?<symbol>^.*)_(?<suffix>.*)';
names = regexp(fileName, expr, 'names');
symbol = names.symbol;

function mlDateNum = asciiToTimeStamp(data,dateFormat,timeFormat,hasHeaderRow)
% Function to convert date from ASCII to MatLab datenum
mlDateNum = datenum(strcat(data.textdata(1+hasHeaderRow:end,1),data.textdata(1+hasHeaderRow:end,2)),[dateFormat timeFormat]);

function trColumns = removeTxtSeps(columnNames)
% Remove trailing quotes from passed parameter
trColumns = cell(1,size(columnNames,2));
for i = 1:size(columnNames,2),
    trColumns{1,i} = strtok(columnNames{1,i}, '"');
end