% Example Script for Pairs Trading

load Example_Data.mat;

x=Assets;   % Price Matrix
d=501;      % Starting Date
window=500; % Moving window for defining pairs
t=1.5;        % Threshold value for defining abnormal behavior
ut=10;      % Peridiocity of pairs updates
C=0;        % Trading cost per trade (in Percentage, default=0.001)

[Results_Total,Results_Long,Results_Short]=pairstrading(x,d,window,t,ut,C);