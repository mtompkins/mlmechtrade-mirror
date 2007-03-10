%% Main cycle reding data from files
function data = importIntraDay
%ImportIntraDay
%  Imports data from *.csv

% List files
files = ls('*.csv');
data = struct();
for j = 1:size(files,1)
    % Import the file
    fileToRead = files(j,1:end);
    data(j).symbol = symbolFromFileName(fileToRead);
    fileData = importdata(fileToRead);
    data(j).fields = removeTxtSeps(fileData.textdata(1,3:end));
    data(j).time = asciiToTimeStamp(fileData);
    data(j).data = fileData.data;
end

%% Process loaded data
%processFinData(data);

%% Internal functions
function symbol = symbolFromFileName(fileName)
expr = '(?<symbol>^.*)_(?<suffix>.*)';
names = regexp(fileName, expr, 'names');
symbol = names.symbol;


function mlDateNum = asciiToTimeStamp(data)
% Function to convert date from ISO yyyyMMdd to MatLab datenum
mlDateNum = datenum(strcat(data.textdata(2:end,1),data.textdata(2:end,2)),'yyyymmddHH:MM');

function trColumns = removeTxtSeps(columnNames)
trColumns = cell(1,size(columnNames,2));
for i = 1:size(columnNames,2)
    trColumns{1,i} = strtok(columnNames{1,i}, '"');
end

%function result = processFinData(finData)
%result = [];