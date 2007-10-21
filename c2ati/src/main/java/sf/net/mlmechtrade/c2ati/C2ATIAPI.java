package sf.net.mlmechtrade.c2ati;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.httpclient.HttpException;
import org.xml.sax.SAXException;

import sf.net.mlmechtrade.c2ati.domain.C2SystemState;
import sf.net.mlmechtrade.c2ati.domain.FillConfirm;
import sf.net.mlmechtrade.c2ati.domain.LatestSignals;
import sf.net.mlmechtrade.c2ati.domain.MultFillConfirmEnum;
import sf.net.mlmechtrade.c2ati.domain.TradingSystem;

public interface C2ATIAPI {

	public abstract void login() throws IOException,
			ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError;

	public abstract void logOff() throws ParserConfigurationException,
			SAXException, IOException, XPathExpressionException, C2ATIError;

	public abstract LatestSignals latestSignals() throws HttpException,
			IOException, ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError;

	public abstract void confirmSig(long quantity, long sigId, String permId)
			throws HttpException, IOException, ParserConfigurationException,
			SAXException, XPathExpressionException, C2ATIError;

	public abstract void cancelConfirmSigId(long sigId) throws HttpException,
			IOException, ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError;

	public abstract void cancelConfirmPermId(String permId)
			throws HttpException, IOException, ParserConfigurationException,
			SAXException, XPathExpressionException, C2ATIError;

	public abstract void multFillConfirm(List<FillConfirm> fillConfirmList,
			MultFillConfirmEnum type) throws HttpException,
			XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError;

	public abstract String multFillConfirmCommandString(
			List<FillConfirm> fillConfirmList, MultFillConfirmEnum type);

	public abstract void multFillConfirmSigId(long sigId) throws HttpException,
			XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError;

	public abstract void multFillConfirmPermId(String permId)
			throws HttpException, XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError;

	public abstract void ackComplete(long sigId) throws HttpException,
			IOException, ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError;

	public abstract void ackc2Fill(String permId) throws HttpException,
			XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError;

	public abstract void ackc2Fill(long sigId) throws HttpException,
			XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError;

	public abstract List<TradingSystem> getAllSignals() throws HttpException,
			IOException, ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError;

	public abstract List<TradingSystem> requestSystemList()
			throws HttpException, IOException, ParserConfigurationException,
			SAXException, XPathExpressionException, C2ATIError;

	public abstract List<C2SystemState> requestSystemSync()
			throws HttpException, IOException, ParserConfigurationException,
			SAXException, XPathExpressionException, C2ATIError;

	public abstract long getServerTime();

	public abstract String getEMail();

	public abstract void setEMail(String mail);

	public abstract void setPassword(String password);

	public abstract String getHost();

	public abstract void setHost(String host);

	public abstract String getSessionId();

	public abstract long getPollInterval();

	public abstract long getClientTime();

	public abstract String getServerIPAddress();

	public abstract String getServerlPort();

	public abstract String getPostedHumanTime();

}