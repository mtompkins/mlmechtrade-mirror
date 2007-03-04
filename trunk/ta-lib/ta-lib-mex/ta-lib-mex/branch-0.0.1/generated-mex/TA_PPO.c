#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>


/*
 * TA_PPO - Percentage Price Oscillator
 * Input Parameters
 * -------------------
 * inReal
 * 
 * Optional Parameters
 * -------------------
 * optInFastPeriod:(From 2 to 100000) Description: Number of period for the fast MA
 * optInSlowPeriod:(From 2 to 100000) Description: Number of period for the slow MA
 * optInMAType:(From ${optInArg.Range.Minimum} to ${optInArg.Range.Maximum}) Description: Type of Moving Average
 *
 * Output
 * -------------------
 * outReal
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
  double * inReal;
/* optional input */
  int	 optInFastPeriod;
  int	 optInSlowPeriod;
  double	 optInMAType;
/* output variables */ 
  int outBegIdx;
  int outNbElement;
  double*	 outReal;
/* input dimentions */ 
  int inSeriesRows;
  int inSeriesCols;
/* error handling */
  TA_RetCode retCode;

/* ----------------- input/output count ----------------- */  
  /*  Check for proper number of arguments. */
  if (nrhs < 1 || nrhs > 4) mexErrMsgTxt("#4 inputs possible #3 optional.");
  if (nlhs != 1) mexErrMsgTxt("#1 output required.");
/* ----------------- INPUT ----------------- */ 
  /* Create a pointer to the input matrix inReal. */
  inReal = mxGetPr(prhs[0]);
  /* Get the dimensions of the matrix input inReal. */
  inSeriesCols = mxGetN(prhs[0]);
  if (inSeriesCols != 1) mexErrMsgTxt("inReal only vector alowed.");
  inSeriesRows = mxGetM(prhs[0]);  
  endIdx = inSeriesRows - 1;  
  startIdx = 0;

 /* Process optional arguments */ 
  if (nrhs >= 1+1) {
	if (!mxIsDouble(prhs[1]) || mxIsComplex(prhs[1]) ||
      mxGetN(prhs[1])*mxGetM(prhs[1]) != 1) 
    	mexErrMsgTxt("Input optInFastPeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInFastPeriod = (int)  mxGetScalar(prhs[1]);
  } else {
  	optInFastPeriod = 12;
  }
  if (nrhs >= 2+1) {
	if (!mxIsDouble(prhs[2]) || mxIsComplex(prhs[2]) ||
      mxGetN(prhs[2])*mxGetM(prhs[2]) != 1) 
    	mexErrMsgTxt("Input optInSlowPeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInSlowPeriod = (int)  mxGetScalar(prhs[2]);
  } else {
  	optInSlowPeriod = 26;
  }
  if (nrhs >= 3+1) {
	if (!mxIsDouble(prhs[3]) || mxIsComplex(prhs[3]) ||
      mxGetN(prhs[3])*mxGetM(prhs[3]) != 1) 
    	mexErrMsgTxt("Input optInMAType must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInMAType =   mxGetScalar(prhs[3]);
  } else {
  	optInMAType = 0;
  }

/* ----------------- OUTPUT ----------------- */
  outReal = mxCalloc(inSeriesRows, sizeof(double));
/* -------------- Invocation ---------------- */

	retCode = TA_PPO(
                   startIdx, endIdx,
                   inReal,
                   optInFastPeriod,
                   optInSlowPeriod,
                   optInMAType,
                   &outBegIdx, &outNbElement,
                   outReal);
/* -------------- Errors ---------------- */
   if (retCode) {
   	   mxFree(outReal);
       mexPrintf("%s%i","Return code=",retCode);
       mexErrMsgTxt(" Error!");
   }
  
   // Populate Output
  plhs[0] = mxCreateDoubleMatrix(outBegIdx+outNbElement,1, mxREAL);
  memcpy(((double *) mxGetData(plhs[0]))+outBegIdx, outReal, outNbElement*mxGetElementSize(plhs[0]));
  mxFree(outReal);  
} /* END mexFunction */
