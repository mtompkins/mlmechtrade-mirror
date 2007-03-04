mltechtrade ta-lib MEX API v0.0.2 Beta

Changes:
2007-03-04 The TA_Initialize and TA_Shutdown added
2007-03-04 TA_Lib 0.3.0 MEX wrappers compiled

API documentation: 
* Corresponds to www.tadoc.org (ta-lib) signatures.

HOW-TO:
self explanatory example:
TA_Initialize;
result = TA_MFI(high,low,close,volume,30);
TA_Shutdown;

Where result resulting array. high,low,close,volume vectors Nx1 all with same N value. Input time series must contain data (NaNs are not expected).

Environment:
* Created for MatLab 2006b Win 32 Environment.
* Linked with TA-Lib Version 0.3.0 (January 2007).
* Compiled with single threaded libraries of TA-Lib. Must be recompiled in order to use in rare, but possible applications.
Notes:
* Compiled with Microsoft Visual C/C++ version 7.1
Warning: 
* .NET Framework should be installed on workstation

Known bugs and limitations:
* Note first elements are filled with 0 and not with NaNs and may be improved in future releases (if needed).
* Time input series must not contain data (not NaNs)!


API documentation: 
* Corresponds to www.tadoc.org (ta-lib) signatures.

HOW-TO:
self explanatory example:
result = TA_MFI(high,low,close,volume,30);

Where result resulting array. high,low,close,volume vectors Nx1 all with same N value. Input time series must contain data (NaNs are not expected).
