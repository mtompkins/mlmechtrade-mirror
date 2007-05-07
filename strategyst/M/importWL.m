function [time, ohlcvd] = importWL(wlFileName)
fid = fopen(wlFileName,'r','ieee-le');
bars = fread(fid, 1, 'int32=>int32');
bars = double(bars);
raw = fread(fid,[32,bars],'uint8=>uint8');
fclose(fid); clear fid;
time = typecast(reshape(raw(1:8,:),1,bars*8),'double') + 693960;
time = time';
ohlcvd = typecast(reshape(raw(9:32,:),1,bars*24),'single');
ohlcvd = reshape(ohlcvd,6,bars)';

