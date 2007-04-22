%% Main cycle reding data from files
function data = importIntraDay(compressEnabled)
%ImportIntraDay
% Imports data from *.csv in Trader Station format. File name formats
% <SYMBOL>_<time scale>.csv
% TODO: CHECK if parameter comress provided and set default to false.
%
if nargin < 1
    compressEnabled = true;
    data.filter = 1;
else
    data.filter = 0;
end  % Use compression by default

%% Prepare output structure
import java.util.HashMap;
data.compressionMap = java.util.HashMap();
data.compressedData = {};
data.marketData = struct();
createGlobalCashe();

%% List files in current directory
files = ls('*.csv');
for j = 1:size(files,1),
    % Import the file
    fileToRead = files(j,1:end);
    data.marketData(j).symbol = symbolFromFileName(fileToRead);
    fileData = importdata(fileToRead);
    data.marketData(j).fields = removeTxtSeps(fileData.textdata(1,3:end));
    data.marketData(j).time = asciiToTimeStamp(fileData);
    data.marketData(j).seriesLen = length(data.marketData(j).time);
    data.marketData(j).data = fileData.data;
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

function mlDateNum = asciiToTimeStamp(data)
% Function to convert date from ISO yyyyMMdd to MatLab datenum
mlDateNum = datenum(strcat(data.textdata(2:end,1),data.textdata(2:end,2)),'yyyymmddHH:MM');

function trColumns = removeTxtSeps(columnNames)
% Remove trailing quotes from passed parameter
trColumns = cell(1,size(columnNames,2));
for i = 1:size(columnNames,2),
    trColumns{1,i} = strtok(columnNames{1,i}, '"');
end