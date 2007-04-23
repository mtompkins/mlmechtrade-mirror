%  Classical Pairs Trading Using MatLab
%
% DESCRIPTION:
%     This function performs the classical pairs trading framework over a
%     matrix of prices using MatLab. The basic idea of pairs trading is to
%     take advantage of market mean reversion behavior in order to make
%     profit out of short and long positions. More details about this type
%     of quantitative trading strategy can be found at the pdf document in
%     the zip file. This function will calculate the total raw and
%     excessive return for each type of trade and also the total with the
%     input parameters d,window,t and ut. 
%
%     Empirical results regarding Classical Pairs Trading can be found at:
%   
%     GATEV, E., GOETZMANN, W. N., ROUWENHORST, K. G. Pairs Trading:
%     Performance of a Relative Value Arbitrage Rule. Working Paper, Yale
%     School of Management. Available at SSRN:    
%     http://ssrn.com/abstract=141615, 1999.
%
%     PERLIN, M. S. Evaluation of Pairs Trading Strategy at Brazilian
%     Financial Market. Unpublished Working Paper, 2006. Available at:
%     http://ssrn.com/abstract=952242
%
%       USAGE:
% [Results_Total,Results_Long,Results_Short]=pairstrading(x,d,window,t,ut,C)
%
%       INPUT:
%               x - A matrix with the prices of all tested assets in the
%               pairs trading framework. The rows represents the time and
%               the collums represents each asset. The function will find a
%               pair for each, and trade according to the Long&Short rules
%               stablished in the word document.
%
%               d - The first trading period of the matrix. For example,
%               supose you have a matrix x with size [1500,40] (1500 prices
%               for 40 assets) and you stablish that d=200, then the
%               obervation 200 is the first trading period.
%
%               window - The size of the rolling window that will be used
%               to find the pairs. Using the last example, if d=200 and
%               window=100, then, for the trading period of 200, the
%               algorithm is going to use the observations 100:199 as a
%               training period.
%
%               t - The threshold parameter which determines what is a
%               unusual behavior. More details at the word document.
%
%               ut - The periodicy that the function will find a pair. As
%               an example, if d=200, window=100 and ut=25, then the
%               function will find a pair for each stock at
%               observations 225,250 and so on. This is good becouse it
%               allows for flexibility in the algorithm.
%
%               C - Transaction Cost, in percentage. More details at pdf document (default C=0.1%).
%              
%
%       OUTPUT:
%               Results_Total - A structure containing the excessive and raw
%               profit and also the series of raw returns from the
%               strategy.
%
%               Results_Long - A structure containing the excessive and raw
%               profit and also the series of raw returns for long
%               positions, only.
%               
%               Results_Short - A structure containing the excessive and raw
%               profit and also the series of raw returns for short
%               positions, only.              
% 
%
%   Author: Marcelo Scherer Perlin 
%   Email:  marceloperlin@gmail.com
%   University of Rio Grande do Sul/ Brazil - 
%   Business School/Department of Finance 
%   Created: November/2006 
%   Last Update: 26-February-2007
%
%   EXAMPLE: The zip file comes with Example_Data.mat which as a matrix
%   called Brazilian_Assets. Such matrix has 1200 observations for 50
%   stocks of the Brazilian financial Market, between the period of
%   03/01/2000 - 06/08/2004.
%
%   Try:
%
%   [Results_Total,Results_Long,Results_Short]=pairstrading(Brazilian_Assets,500,499,2,10); 
%
%   Feel free to use it and modify it for your own interest. 
%
%   Please send your review to the matlab exchange site. If you have a
%   suggestion that will improve the code, please send it to me.


function [Results_Total,Results_Long,Results_Short]=pairstrading(x,d,window,t,ut,C)

[n1,n2]=size(x);

if nargin==5
    C=0.001;
end

% Calculation of first execution parameters

ut2=ut;
ut1=ut;
k1=1;
ret=price2ret(x);

% Prealocation of Large Matrixes

B_Long=zeros(n1-d+1,n2);
B_Short=zeros(n1-d+1,n2);

P_Long=zeros(n1-d+1,n2);
P_Short=zeros(n1-d+1,n2);

BsignsT=zeros(n1-d+1,n2);
dist=zeros(n1-d+1,n2);

fprintf(1,'\nPerforming Pairs Trading. Please Hold.\n')

% Main Engine

for k=1:n1-d+1
    
    % x2 is the training period for each time t. It evolves with k and
    % doesn't use the information of the observation d, which is the first
    % trading period in the simulation
    
    x2=x(d-window+k-1:d+k-2,:);
    
    x3=mapstd(x2')'; % Normalization of x2

    % Find/change the Pairs for each k periods based on the quadratic error criteria.
    
    if k==1||ut==1
        [p]=pairs(x3,'qerror');
    else
        if k==ut1
            k1=k1+1;
            ut1=k1*ut2;
            [p]=pairs(x3,'qerror');
        end
    end

    % Trade in each day according to the logic of pairs trading
    
    for i=1:n2
        dist(k,i)=x3(window,i)-x3(window,p(i,1));
        if abs(dist(k,i))>t
            if dist(k,i)>0
                B_Short(k,i)=-1;
                B_Long(k,p(i,1))=+1;
            else
                B_Short(k,p(i,1))=-1;
                B_Long(k,i)=+1;
            end
        end
        
    end
    
    % Calculation of the Total Buy_Signs Matrix
    
    BsignsT(k,:)=B_Long(k,:)+B_Short(k,:);
    
    % Calculation of the number of trades for each day
    
    n_AssetsT(k,1)=sum(abs(BsignsT(k,:)));
    n_AssetsL(k,1)=sum(abs(B_Long(k,:)));
    n_AssetsS(k,1)=sum(abs(B_Short(k,:)));

    % A simple fix so the cases where there is no trade, the weights
    % of the portfolio for each day are not divided by 0, but by 1.
    
    if n_AssetsT(k,1)==0
        n_AssetsT(k,1)=1;
        n_AssetsL(k,1)=1;
        n_AssetsS(k,1)=1;
    end
    
    % Calculation of the original returns (without transaction costs) for
    % each day, for each operaion of Short or Long. Remember that 1
    % observation is lost in the transformantion of prices to logarithm
    % returns. Thats why ret(d+k-2,:) is using d+k-2, instead of d+k-1.
    
    P_Short(k,:)=B_Short(k,:).*ret(d+k-2,:).*((abs(B_Short(k,:))/n_AssetsS(k,1)));
    P_Long(k,:) =B_Long(k,:) .*ret(d+k-2,:).*((B_Long(k,:)/n_AssetsL(k,1)));
    P_Total(k,:)=BsignsT(k,:).*ret(d+k-2,:).*((abs(BsignsT(k,:))/n_AssetsT(k,1)));
       
end

% Find the number of trades required in each type of operation. More
% details at trades.m

[nTL]=trades(B_Long);
[nTS]=trades(abs(B_Short));

% Weights of the assets in the calculation of the return from a benchmark
% portfolio

wT_Short=sum(abs(B_Short))/(n1-d+1);
wT_Long=sum(abs(B_Long))/(n1-d+1);

% Series of the transactions, which will help the calculation of the series
% of raw returns.

TransactionSeriesT=sum(nTS+nTL,2)~=0;
TransactionSeriesL=sum(nTL,2)~=0;
TransactionSeriesS=sum(nTS,2)~=0;

% Number of transactions of each type of trade

Trans_Long=sum(TransactionSeriesL~=0);
Trans_Short=sum(TransactionSeriesS~=0);
Trans_Total=sum(TransactionSeriesT);

% Calculation of Raw Returns Series for each type of trade with transaction
% cost of 0.1% per operation.

Results_Long.Raw_Series=sum(P_Long,2)+TransactionSeriesL*log((1-C)/(1+C));
Results_Short.Raw_Series=sum(P_Short,2)+TransactionSeriesS*log((1-C)/(1+C));
Results_Total.Raw_Series=Results_Short.Raw_Series+Results_Long.Raw_Series;

% Calculation of total Raw Returns for each type of position

Results_Short.Raw_Profit=sum(sum(P_Short))+log((1-C)/(1+C))*(Trans_Short);
Results_Long.Raw_Profit=sum(sum(P_Long))+log((1-C)/(1+C))*(Trans_Long);
Results_Total.Raw_Profit=sum(sum(P_Total))+log((1-C)/(1+C))*(Trans_Total);

% Calculation of Excessive Return for each type of trade

Results_Short.Exc_Profit=sum(sum(P_Short))+log((1-C)/(1+C))*(Trans_Short-n2)-sum(wT_Short.*-sum(ret(d-1:end,:)));
Results_Long.Exc_Profit=sum(sum(P_Long))+log((1-C)/(1+C))*(Trans_Long-n2)-sum(wT_Long.*sum(ret(d-1:end,:)));
Results_Total.Exc_Profit=sum(sum(P_Total))+(Trans_Total-2*n2)*log((1-C)/(1+C))-sum(wT_Short.*-sum(ret(d-1:end,:)))-sum(wT_Long.*sum(ret(d-1:end,:)));
