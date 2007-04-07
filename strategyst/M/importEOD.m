%% Main cycle reding data from files
function data = importEOD()
%ImportIntraDay
% Imports data from *.CSV from Quoets Plus Export ASCII format:
% Symbol, Text(D), Data(YYMMDD),Open,High,Low,Close,Vol 1's
%
compressEnabled = false; % Without compression

%% Prepare output structure
data.compressionMap = java.util.HashMap();
data.compressedData = {};

%% List files in current directory
files = ls('*.csv');
for j = 1:size(files,1),
    % Import the file
    fileToRead = files(j,1:end)
    
    data.marketData(j).symbol = symbolFromFileName(fileToRead);
    fileData = importdata(fileToRead);
    data.marketData(j).fields = {'Open','High', 'Low', 'Close', 'Volume'};
    data.marketData(j).time = getTimeStamp(fileData);
    data.marketData(j).seriesLen = length(data.marketData(j).time);
    data.marketData(j).data = fileData.data(:,2:end);
    if (compressEnabled),
        data = compress(data, j);
    end % end if
end % end for

%% helper functios
function symbol = symbolFromFileName(fileName)
% Retrieve symbol from TS export file name
expr = '(?<symbol>^.*).CSV';
names = regexp(fileName, expr, 'names');
symbol = names.symbol;

function mlDateNum = getTimeStamp(td)
% Format for yymmdd => ML timeStamp
y = floor(td.data(:,1)./10000);
i2kyy =  find(y < 50);
i19yy =  find(y >= 50);
yyyy = NaN(size(y));
yyyy(i2kyy) = 2000+y(i2kyy);
yyyy(i19yy) = 1900+y(i19yy);
clear i2kyy i19yy y;
% Month
m = mod(floor(td.data(:,1)./100),100);
% Day
d = mod(td.data(:,1),100);
% Function to convert date from ISO yyyyMMdd to MatLab datenum
mlDateNum = datenum(yyyy,m,d);