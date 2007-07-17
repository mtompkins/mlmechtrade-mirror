% Example Script for performance assessemnt for random returns and random
% trading signals.

% ***** Options ******

n_Ret=500;
n_Assets=150;

% ********************

Ret_Matrix=randn(n_Ret,n_Assets)*.01;    % Make believe this is matrix with 50 dailly returns from 15 stocks

[nr,nc]=size(Ret_Matrix);

Rand_M=abs(rand(nr,nc)); % Random numbers for Weight_Matrix creation

Weight_Matrix=(Rand_M./repmat(sum(Rand_M,2),1,nc)); % Make believe this is the portfolio weight matrix
Mkt_Ret_Vec=sum(Ret_Matrix,2)/nc;                   % Make believe this is a market portfolio (eg. sp500)


[Out]=Get_Strat_Ind(Weight_Matrix,Ret_Matrix,Mkt_Ret_Vec)