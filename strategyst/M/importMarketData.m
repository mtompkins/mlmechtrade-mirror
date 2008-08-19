%% Main cycle reding data from files
function data = importIntraDay(fileMask,dateFormat,timeFormat,hasHeaderRow)
%ImportIntraDay
% Imports data from FileMask in Trader Station or similar ASCII format.
% <SYMBOL>_<time scale>.csv
% Default fileMask '*.csv'
% Default dataFormat 'yyyymmdd'
% Default timeFormat 'HH:MM' for EOD specify ''
% hasHeaderRow = true and OHLCV order expected for missing header
% 
% example invocation:
% importIntraDay('*.csv','mm/dd/yyyy','HHMM')

%% Defaults
% File Mask
if nargin < 1
    fileMask = '*.csv';
end
% Default data
if nargin < 2
    dateFormat = 'yyyymmdd';
end
% Default time    
if nargin < 3
    timeFormat = 'HH:MM';
end
% Default of hasHeaderRow
if nargin < 4
    hasHeaderRow = 1;
end

%% Prepare output structure
data.marketData = struct();

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
        if isstruct(fileData)
            tFields = removeTxtSeps(fileData.textdata(1,3:end));            
        else
            tFields = {'Open','High','Low','Close','Volume'};
        end
        [tTime hasTimeAsNum] = asciiToTimeStamp(fileData,dateFormat,timeFormat,hasHeaderRow);
        if (hasTimeAsNum),
            tData = fileData.data(:,2:end);
        else
            tData = fileData.data;        
        end
    end
    data.marketData(j).fields = tFields; 
    data.marketData(j).time = tTime;
    data.marketData(j).seriesLen = length(tTime);
    data.marketData(j).data = tData;
end % end for


%% helper functios

function symbol = symbolFromFileName(fileName)
% Retrieve symbol from TS export file name
expr = '(?<symbol>^.*)_(?<suffix>.*)';
names = regexp(fileName, expr, 'names');
symbol = names.symbol;

function [mlDateNum hasTimeAsNum] = asciiToTimeStamp(data,dateFormat,timeFormat,hasHeaderRow)
% Function to convert date from ASCII to MatLab datenum
if isempty(data.textdata{2,2}) && strcmp(timeFormat,'HHMM'),
    mlDateNum = datenum(data.textdata(1+hasHeaderRow:end,1),dateFormat);
    % add time component HHMM
    tmpTime = data.data(:,1);
    mlDateNum = mlDateNum + (floor(tmpTime/100)*60+rem(tmpTime,100))/1440;
    hasTimeAsNum = true;
elseif isempty(data.textdata{2,2}) && strcmp(timeFormat,''),
    % EOD data
    mlDateNum = datenum(data.textdata(1+hasHeaderRow:end,1),dateFormat);
    hasTimeAsNum = false;
else
    mlDateNum = datenum(strcat(data.textdata(1+hasHeaderRow:end,1),data.textdata(1+hasHeaderRow:end,2)),[dateFormat timeFormat]);
    hasTimeAsNum = false;
end

function trColumns = removeTxtSeps(columnNames)
% Remove trailing quotes from passed parameter
trColumns = cell(1,size(columnNames,2));
for i = 1:size(columnNames,2),
    trColumns{1,i} = strtok(columnNames{1,i}, '"');
end