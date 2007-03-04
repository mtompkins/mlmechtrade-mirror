#include "ta_libc.h" 
#include "mex.h" 
#include <string.h>


/*
 * TA_BBANDS - Bollinger Bands
 * Input Parameters
 * -------------------
 * inReal
 * 
 * Optional Parameters
 * -------------------
 * optInTimePeriod:(From 2 to 100000) Description: Number of period
 * optInDeviationsup:(From -3.000000e+37 to 3.000000e+37) Description: Deviation multiplier for upper band
 * optInDeviationsdown:(From -3.000000e+37 to 3.000000e+37) Description: Deviation multiplier for lower band
 * optInMAType:(From ${optInArg.Range.Minimum} to ${optInArg.Range.Maximum}) Description: Type of Moving Average
 *
 * Output
 * -------------------
 * outRealUpperBand
 * outRealMiddleBand
 * outRealLowerBand
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
  int	 optInTimePeriod;
  double	 optInDeviationsup;
  double	 optInDeviationsdown;
  double	 optInMAType;
/* output variables */ 
  int outBegIdx;
  int outNbElement;
  double*	 outRealUpperBand;
  double*	 outRealMiddleBand;
  double*	 outRealLowerBand;
/* input dimentions */ 
  int inSeriesRows;
  int inSeriesCols;
/* error handling */
  TA_RetCode retCode;

/* ----------------- input/output count ----------------- */  
  /*  Check for proper number of arguments. */
  if (nrhs < 1 || nrhs > 5) mexErrMsgTxt("#5 inputs possible #4 optional.");
  if (nlhs != 3) mexErrMsgTxt("#3 output required.");
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
    	mexErrMsgTxt("Input optInTimePeriod must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInTimePeriod = (int)  mxGetScalar(prhs[1]);
  } else {
  	optInTimePeriod = 5;
  }
  if (nrhs >= 2+1) {
	if (!mxIsDouble(prhs[2]) || mxIsComplex(prhs[2]) ||
      mxGetN(prhs[2])*mxGetM(prhs[2]) != 1) 
    	mexErrMsgTxt("Input optInDeviationsup must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInDeviationsup =   mxGetScalar(prhs[2]);
  } else {
  	optInDeviationsup = 2.000000e+0;
  }
  if (nrhs >= 3+1) {
	if (!mxIsDouble(prhs[3]) || mxIsComplex(prhs[3]) ||
      mxGetN(prhs[3])*mxGetM(prhs[3]) != 1) 
    	mexErrMsgTxt("Input optInDeviationsdown must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInDeviationsdown =   mxGetScalar(prhs[3]);
  } else {
  	optInDeviationsdown = 2.000000e+0;
  }
  if (nrhs >= 4+1) {
	if (!mxIsDouble(prhs[4]) || mxIsComplex(prhs[4]) ||
      mxGetN(prhs[4])*mxGetM(prhs[4]) != 1) 
    	mexErrMsgTxt("Input optInMAType must be a scalar.");
   	/* Get the scalar input optInTimePeriod. */
   	optInMAType =   mxGetScalar(prhs[4]);
  } else {
  	optInMAType = 0;
  }

/* ----------------- OUTPUT ----------------- */
  outRealUpperBand = mxCalloc(inSeriesRows, sizeof(double));
  outRealMiddleBand = mxCalloc(inSeriesRows, sizeof(double));
  outRealLowerBand = mxCalloc(inSeriesRows, sizeof(double));
/* -------------- Invocation ---------------- */

	retCode = TA_BBANDS(
                   startIdx, endIdx,
                   inReal,
                   optInTimePeriod,
                   optInDeviationsup,
                   optInDeviationsdown,
                   optInMAType,
                   &outBegIdx, &outNbElement,
                   outRealUpperBand,                   outRealMiddleBand,                   outRealLowerBand);
/* -------------- Errors ---------------- */
   if (retCode) {
   	   mxFree(outRealUpperBand);
   	   mxFree(outRealMiddleBand);
   	   mxFree(outRealLowerBand);
       mexPrintf("%s%i","Return code=",retCode);
       mexErrMsgTxt(" Error!");
   }
  
   // Populate Output
  plhs[0] = mxCreateDoubleMatrix(outBegIdx+outNbElement,1, mxREAL);
  memcpy(((double *) mxGetData(plhs[0]))+outBegIdx, outRealUpperBand, outNbElement*mxGetElementSize(plhs[0]));
  mxFree(outRealUpperBand);  
  plhs[1] = mxCreateDoubleMatrix(outBegIdx+outNbElement,1, mxREAL);
  memcpy(((double *) mxGetData(plhs[1]))+outBegIdx, outRealMiddleBand, outNbElement*mxGetElementSize(plhs[1]));
  mxFree(outRealMiddleBand);  
  plhs[2] = mxCreateDoubleMatrix(outBegIdx+outNbElement,1, mxREAL);
  memcpy(((double *) mxGetData(plhs[2]))+outBegIdx, outRealLowerBand, outNbElement*mxGetElementSize(plhs[2]));
  mxFree(outRealLowerBand);  
} /* END mexFunction */
