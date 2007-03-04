#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>


/*
 * TA_STOCH - Stochastic
 * Input Parameters
 * -------------------
 * high
 * low
 * close
 * 
 * Optional Parameters
 * -------------------
 * optInFastKPeriod:(From 1 to 100000) Description: Time period for building the Fast-K line
 * optInSlowKPeriod:(From 1 to 100000) Description: Smoothing for making the Slow-K line. Usually set to 3
 * optInSlowKMA:(From ${optInArg.Range.Minimum} to ${optInArg.Range.Maximum}) Description: Type of Moving Average for Slow-K
 * optInSlowDPeriod:(From 1 to 100000) Description: Smoothing for making the Slow-D line
 * optInSlowDMA:(From ${optInArg.Range.Minimum} to ${optInArg.Range.Maximum}) Description: Type of Moving Average for Slow-D
 *
 * Output
 * -------------------
 * outSlowK
 * outSlowD
 * 
 */
 
/* The gateway routine */
void mexFunction(int nlhs, mxArray *plhs[],
                 int nrhs, const mxArray *prhs[])
{
/* ----------------- Variables ----------------- */
/* input variables */
/* mandatory input */
  int startIdx;
  int endIdx;
  double * high;
  double * low;
  double * close;
/* optional input */
  int	 optInFastKPeriod;
  int	 optInSlowKPeriod;
  double	 optInSlowKMA;
  int	 optInSlowDPeriod;
  double	 optInSlowDMA;
/* output variables */ 
  int outBegIdx;
  int outNbElement;
  double*	 outSlowK;
  double*	 outSlowD;
/* input dimentions */ 
  int inSeriesRows;
  int inSeriesCols;
/* error handling */
  TA_RetCode retCode;

/* ----------------- input/output count ----------------- */  
  /*  Check for proper number of arguments. */
  if (nrhs < 3 || nrhs > 8) mexErrMsgTxt("#8 inputs possible #5 optional.");
  if (nlhs != 2) mexErrMsgTxt("#2 output required.");
/* ----------------- INPUT ----------------- */ 
  /* Create a pointer to the input matrix high. */
  high = mxGetPr(prhs[0]);
  /* Get the dimensions of the matrix input high. */
  inSeriesCols = mxGetN(prhs[0]);
  if (inSeriesCols != 1) mexErrMsgTxt("high only vector alowed.");
  /* Create a pointer to the input matrix low. */
  low = mxGetPr(prhs[1]);
  /* Get the dimensions of the matrix input low. */
  inSeriesCols = mxGetN(prhs[1]);
  if (inSeriesCols != 1) mexErrMsgTxt("low only vector alowed.");
  /* Create a pointer to the input matrix close. */
  close = mxGetPr(prhs[2]);
  /* Get the dimensions of the matrix input close. */
  inSeriesCols = mxGetN(prhs[2]);
  if (inSeriesCols != 1) mexErrMsgTxt("close only vector alowed.");
  inSeriesRows = mxGetM(prhs[2]);  
  endIdx = inSeriesRows - 1;  
  startIdx = 0;

 /* Process optional arguments */ 
  if (nrhs >= 3+1) {
	if (!mxIsDouble(prhs[3]) || mxIsComplex(prhs[3]) ||
      mxGetN(prhs[3])*mxGetM(prhs[3]) != 1) 
    	mexErrMsgTxt("Input optInFastKPeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInFastKPeriod = (int)  mxGetScalar(prhs[3]);
  } else {
  	optInFastKPeriod = 5;
  }
  if (nrhs >= 4+1) {
	if (!mxIsDouble(prhs[4]) || mxIsComplex(prhs[4]) ||
      mxGetN(prhs[4])*mxGetM(prhs[4]) != 1) 
    	mexErrMsgTxt("Input optInSlowKPeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInSlowKPeriod = (int)  mxGetScalar(prhs[4]);
  } else {
  	optInSlowKPeriod = 3;
  }
  if (nrhs >= 5+1) {
	if (!mxIsDouble(prhs[5]) || mxIsComplex(prhs[5]) ||
      mxGetN(prhs[5])*mxGetM(prhs[5]) != 1) 
    	mexErrMsgTxt("Input optInSlowKMA must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInSlowKMA =   mxGetScalar(prhs[5]);
  } else {
  	optInSlowKMA = 0;
  }
  if (nrhs >= 6+1) {
	if (!mxIsDouble(prhs[6]) || mxIsComplex(prhs[6]) ||
      mxGetN(prhs[6])*mxGetM(prhs[6]) != 1) 
    	mexErrMsgTxt("Input optInSlowDPeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInSlowDPeriod = (int)  mxGetScalar(prhs[6]);
  } else {
  	optInSlowDPeriod = 3;
  }
  if (nrhs >= 7+1) {
	if (!mxIsDouble(prhs[7]) || mxIsComplex(prhs[7]) ||
      mxGetN(prhs[7])*mxGetM(prhs[7]) != 1) 
    	mexErrMsgTxt("Input optInSlowDMA must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInSlowDMA =   mxGetScalar(prhs[7]);
  } else {
  	optInSlowDMA = 0;
  }

/* ----------------- OUTPUT ----------------- */
  outSlowK = mxCalloc(inSeriesRows, sizeof(double));
  outSlowD = mxCalloc(inSeriesRows, sizeof(double));
/* -------------- Invocation ---------------- */

	retCode = TA_STOCH(
                   startIdx, endIdx,
                   high,
                   low,
                   close,
                   optInFastKPeriod,
                   optInSlowKPeriod,
                   optInSlowKMA,
                   optInSlowDPeriod,
                   optInSlowDMA,
                   &outBegIdx, &outNbElement,
                   outSlowK,                   outSlowD);
/* -------------- Errors ---------------- */
   if (retCode) {
   	   mxFree(outSlowK);
   	   mxFree(outSlowD);
       mexPrintf("%s%i","Return code=",retCode);
       mexErrMsgTxt(" Error!");
   }
  
   // Populate Output
  plhs[0] = mxCreateDoubleMatrix(outBegIdx+outNbElement,1, mxREAL);
  memcpy(((double *) mxGetData(plhs[0]))+outBegIdx, outSlowK, outNbElement*mxGetElementSize(plhs[0]));
  mxFree(outSlowK);  
  plhs[1] = mxCreateDoubleMatrix(outBegIdx+outNbElement,1, mxREAL);
  memcpy(((double *) mxGetData(plhs[1]))+outBegIdx, outSlowD, outNbElement*mxGetElementSize(plhs[1]));
  mxFree(outSlowD);  
} /* END mexFunction */
