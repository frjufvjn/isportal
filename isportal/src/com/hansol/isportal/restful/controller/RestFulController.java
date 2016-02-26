package com.hansol.isportal.restful.controller;

import com.hansol.isportal.dbservice.service.DbSvcService;
import com.hansol.isportal.dbservice.service.DbSvcServiceImpl;
import com.hansol.isportal.dbservice.service.MainService;
import com.hansol.isportal.hostservice.BigEndianByteHandler;
import com.hansol.isportal.hostservice.ByteBufferUtil;
import com.hansol.isportal.hostservice.LittleEndianByteHandler;
import com.hansol.isportal.hostservice.PBXSocketConnector;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/data"})
public class RestFulController
{
	@Autowired
	private MainService mainService;
		
	@RequestMapping(value="/dbsvc-r", produces="application/json; charset=utf8")
	@ResponseBody
	public Object dbsvcReadController(@RequestBody Map<String,Object> paramMap, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");
		response.addHeader("Access-Control-Allow-Headers", "X-Requested-With");
		
		DbSvcServiceImpl ds = new DbSvcServiceImpl();
		List<Map<String,Object>> res = ds.getListParam(paramMap);
		System.out.println(res);
		return res;
	}
	
	@RequestMapping(value="/dbsvc-u", produces="application/json; charset=utf8")
	@ResponseBody
	public Object dbsvcUpdateController(@RequestBody Map<String,Object> paramMap, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");
		response.addHeader("Access-Control-Allow-Headers", "X-Requested-With");
		
		DbSvcServiceImpl ds = new DbSvcServiceImpl();
		System.out.println("??????????????? " +ds.doUpdate(paramMap));
		
		return null;
	}
	
	@RequestMapping(value="/dbsvc-i", produces="application/json; charset=utf8")
	@ResponseBody
	public Object dbsvcInsertController(@RequestBody List<Map<String,Object>> paramList, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");
		response.addHeader("Access-Control-Allow-Headers", "X-Requested-With");
		
		boolean svcRes = true;
		if(!paramList.isEmpty()) {
			DbSvcServiceImpl ds = new DbSvcServiceImpl();
			svcRes = ds.doInsert(paramList);
		}
		
		Map<String,Object> res = new HashMap<String,Object>();
		if(svcRes) {
			res.put("ResultCode", "0");
			res.put("ResultMessage", "Successfully completed");
		} else {
			res.put("ResultCode", "-1");
			res.put("ResultMessage", "Service Failed");
		}
		
		return res;
	}
	
	@RequestMapping(value="/dbsvc-p", produces="application/json; charset=utf8")
	@ResponseBody
	public Map<String,Object> dbsvcProcedureController(@RequestBody Map<String,Object> paramMap, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");
		response.addHeader("Access-Control-Allow-Headers", "X-Requested-With");
		
		Map<String,Object> resultMap = null;
		if(!paramMap.isEmpty()) {
			DbSvcServiceImpl ds = new DbSvcServiceImpl();
			resultMap = ds.doProcedure(paramMap); 
		}
		
		return resultMap;
	}
	
	@RequestMapping(value="/svctest-1", produces="application/json; charset=utf8")
	@ResponseBody
	public Map<String,Object> svctest1(@RequestBody Map<String,Object> paramMap, HttpServletResponse response) throws Exception {
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");
		response.addHeader("Access-Control-Allow-Headers", "X-Requested-With");
		
		System.out.println(paramMap);
		
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("ResultCode", "0");
		res.put("ResultMessage", "no text");
		List<Map<String,Object>> resList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> hm = new HashMap<String,Object>();
		
		hm.put("a", 1);
		hm.put("b", 2);
		hm.put("c", "dd");
		
		resList.add(0, hm);
		resList.add(1, hm);
		resList.add(2, hm);
		
		res.put("list", resList);
		System.out.println(res);
		return res;
	}
	
	@RequestMapping("/dbsvc1/{svcnm}/{param}")
	public Object dbsvctest(@PathVariable String svcnm, @PathVariable String param) throws Exception {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("db_svc_no", svcnm);
		paramMap.put("c2", param);
		DbSvcServiceImpl ds = new DbSvcServiceImpl();
		List<Map<String,Object>> res = ds.getListParam(paramMap);
		System.out.println(res);
		return res;
	}
	
	@RequestMapping(value="/paramtest/{p1}", produces="application/json; charset=utf8")
	public String paramtest(@PathVariable String p1) {
		System.out.println("p1 : " + p1);
		return p1;
	}
	
	@RequestMapping(value={"/owners/{ownerId}/pets/{petId}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	public void findPet(@MatrixVariable Map<String, Object> matrixVars)
	{
		System.out.println(matrixVars.size());
		System.out.println(matrixVars.get("r"));
	}

	@RequestMapping({"/matrix/{dummy1}/{dummy2}"})
	public void doDummy01(@PathVariable("dummy1") String dummy1, @PathVariable("dummy2") String dummy2Str, @MatrixVariable(pathVar="dummy2") Map<String, List<String>> dummy2)
	{
		System.out.println("dummy1 : " + dummy1);
		System.out.println("dummy2 : " + dummy2Str);
		System.out.println(dummy2.size());
		
		List<List<String>> d2 = map2List(dummy2);
		String paramStr = "";
		for (int i = 0; i < d2.size(); i++)
		{
			List<String> elementVal = (List)d2.get(i);
			System.out.println(elementVal);
		}
	}

	@RequestMapping(value={"/{stocks}/{account}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	public String showPortfolioValuesWithAccountInfo(@MatrixVariable(pathVar="stocks") Map<String, List<String>> stocks, @MatrixVariable(pathVar="account") Map<String, List<String>> accounts)
	{
		System.out.println((String)((List)stocks.get("BT.A")).get(0));

		List<List<String>> stocksView = map2List(stocks);
		for (int i = 0; i < stocksView.size(); i++) {
			System.out.println(stocksView.get(i));
		}
		List<List<String>> accountDetails = map2List(accounts);
		for (int i = 0; i < accountDetails.size(); i++) {
			System.out.println(accountDetails.get(i));
		}
		return "stocks";
	}

	private List<List<String>> map2List(Map<String, List<String>> sMap)
	{
		List<List<String>> outlist = new ArrayList();
		Collection<Map.Entry<String, List<String>>> sSet = sMap.entrySet();
		for (Map.Entry<String, List<String>> entry : sSet)
		{
			List<String> rowList = new ArrayList();

			String name = (String)entry.getKey();
			rowList.add(name);

			List<String> stock = (List)entry.getValue();
			rowList.addAll(stock);
			outlist.add(rowList);
		}
		return outlist;
	}
	
	@RequestMapping("/jsonreqtest")
	@ResponseBody
	public Object jsonRequestTest(@RequestBody List<Object> param, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");
		response.addHeader("Access-Control-Allow-Headers", "X-Requested-With");
		
		System.out.println(param.toString());
		
		for (int i = 0; i < param.size(); i++) {
			Map<String, Object> paramMap = (HashMap<String,Object>)param.get(i);
			Iterator<String> keys = paramMap.keySet().iterator();
			System.out.println("------------------------------------------");
			while (keys.hasNext()) {
				String key = (String) keys.next();
				System.out.println(key + " : " + paramMap.get(key));
			}
		}
		
		return param;
	}

	@RequestMapping(value="/svcnm/{parameter}/{cmd}/{param}", produces="application/json; charset=utf8")
	@ResponseBody
	public Object doHostService(
			@PathVariable String parameter, 
			@PathVariable String cmd, 
			@PathVariable String param, 
			HttpServletResponse response)
			throws SocketTimeoutException, UnsupportedEncodingException, UnknownHostException, IOException
	{
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");
		response.addHeader("Access-Control-Allow-Headers", "X-Requested-With");
		
		return executeHostService(parameter, cmd, param);
	}

	private Object executeHostService(String parameter, String cmd, String param)
			throws UnsupportedEncodingException, IOException, UnknownHostException, SocketTimeoutException
	{
		String[] params = { cmd, param };
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("parameter", parameter);

		List<Object> headerList = this.mainService.getHeader(paramMap);
		List<Object> requestList = this.mainService.getRequest(paramMap);

		ByteBufferUtil bodyPacketBuffer = new ByteBufferUtil();
		for (int i = 0; i < requestList.size(); i++)
		{
			HashMap<String, String> hm = (HashMap)requestList.get(i);
			bodyPacketBuffer.append(getValue(hm, "REQ_BODY", 0, params));
		}
		byte[] bodyPacket = bodyPacketBuffer.toBytes();

		String charset = Charset.defaultCharset().toString();
		System.out.println("charset : " + charset);
		if (charset.equals("UTF-8"))
		{
			String str = new String(bodyPacket, "UTF-8");
			bodyPacket = str.getBytes("EUC-KR");
		}
		int bodyLength = bodyPacket.length;

		ByteBufferUtil headerPacketBuffer = new ByteBufferUtil();
		for (int i = 0; i < headerList.size(); i++)
		{
			HashMap<String, String> hm = (HashMap)headerList.get(i);
			headerPacketBuffer.append(getValue(hm, "HEADER", bodyLength, params));
		}
		byte[] headerPacket = headerPacketBuffer.toBytes();

		int headerLength = headerPacket.length;
		byte[] sendPacket = new byte[headerLength + bodyLength];
		System.arraycopy(headerPacket, 0, sendPacket, 0, headerLength);
		System.arraycopy(bodyPacket, 0, sendPacket, headerLength, bodyLength);

		System.out.println("########################## Call Socket!!");
		PBXSocketConnector sock = new PBXSocketConnector();
		sock.connect("10.1.15.130", 9201);
		byte[] rcvPacket = sock.call(sendPacket);
		System.out.println("##########################");
		System.out.println(new String(rcvPacket, charset));
		sock.close();


		Map result = new HashMap();
		List<Object> responseList = this.mainService.getResponse(paramMap);
		List<Object> responseOccurs = this.mainService.getResponseList(paramMap);

		int resBodyLength = 0;
		for (int i = 0; i < headerList.size(); i++)
		{
			HashMap<String, String> hm = (HashMap)headerList.get(i);
			String fldNm = (String)hm.get("FIELD_NM");
			int value = BigEndianByteHandler.byteToInt(rcvPacket, 4 * i);
			System.out.println("value : " + value);
			result.put(fldNm, Integer.valueOf(value));
			if ("BODY_LENGTH".equals(fldNm)) {
				resBodyLength = value;
			}
		}
		byte[] resBodyPacket = new byte[resBodyLength];
		System.arraycopy(rcvPacket, 12, resBodyPacket, 0, resBodyLength);

		InputStreamReader isr = null;
		isr = new InputStreamReader(new ByteArrayInputStream(resBodyPacket), "EUC-KR");
		BufferedReader rcvBuf = new BufferedReader(isr);


		ArrayList<String> bodyLines = new ArrayList();
		String line = "";

		int idx = 0;
		String reasonTxt = "";
		String resultCode = "";
		while ((line = rcvBuf.readLine()) != null)
		{
			if ((line != null) && (line.length() > 0))
			{
				String[] values = line.split("=");
				if (values.length == 2) {
					if ("RESULT".equals(values[0].trim())) {
						resultCode = values[1].trim();
					} else if ("REASON".equals(values[0].trim())) {
						reasonTxt = values[1].trim();
					}
				}
			}
			bodyLines.add(line);
		}
		for (int i = 0; i < responseList.size(); i++)
		{
			HashMap<String, Object> hm = (HashMap)responseList.get(i);
			String loopYn = (String)hm.get("LOOP_YN");
			if ("Y".equalsIgnoreCase(loopYn))
			{
				List<Object> listValue = new ArrayList();

				String name = (String)hm.get("FIELD_NM");
				for (int j = idx; j < bodyLines.size(); j++)
				{
					String listLine = ((String)bodyLines.get(j)).trim();
					if ((listLine == null) || (listLine.length() < 1) || ("-".equals(listLine.substring(0, 1))) || ("=".equals(listLine.substring(0, 1)))) {
						break;
					}
					listValue.add(parseList(listLine, responseOccurs));
					idx++;
				}
				result.put(name, listValue);
			}
			else
			{
				String name = (String)hm.get("FIELD_NM");
				String fldType = (String)hm.get("FIELD_TYPE");
				String defaultValue = (String)hm.get("DEFAULT_VAL");
				String delimeter = (String)hm.get("FIELD_IN_OUT");
				if (!"SKIP".equalsIgnoreCase(fldType))
				{
					if ((delimeter == null) || ("".equals(delimeter.trim()))) {
						delimeter = "\\s+";
					}
					String fieldValue = "";
					if ("LINE".equals(fldType.toUpperCase()))
					{
						fieldValue = ((String)bodyLines.get(idx)).trim();
					}
					else
					{
						String[] str = ((String)bodyLines.get(idx)).split(delimeter);
						fieldValue = str[(str.length - 1)].trim();
					}
					if (fieldValue == null) {
						fieldValue = defaultValue;
					}
					result.put(name, fieldValue);
				}
				idx++;
			}
		}
		return result;
	}

	private HashMap<String, Object> parseList(String line, List<Object> responseOccurs)
	{
		String delimeter = "\\s+";
		String[] values = line.split(delimeter);
		HashMap<String, Object> result = new HashMap();
		for (int i = 0; i < responseOccurs.size(); i++)
		{
			HashMap<String, String> hm = (HashMap)responseOccurs.get(i);
			String name = (String)hm.get("FIELD_NM");
			String defaultValue = (String)hm.get("DEFAULT_VAL");
			String fieldValue = "";
			if (values.length > i) {
				fieldValue = values[i];
			}
			if ((fieldValue == null) || (fieldValue == "")) {
				fieldValue = defaultValue;
			}
			if (name.equals("EXT_IP"))
			{
				String[] fVals = fieldValue.split(":");
				if (fVals.length > 1) {
					fieldValue = fVals[1].split("@")[1];
				}
			}
			result.put(name, fieldValue);
		}
		return result;
	}

	private byte[] getValue(HashMap<String, String> rowValue, String Type, int BodyLen, String[] params)
	{
		String fldName = (String)rowValue.get("FIELD_NM");
		String defaultValue = rowValue.get("DEFAULT_VAL") != null ? (String)rowValue.get("DEFAULT_VAL") : "";
		int fldLen = rowValue.get("FIELD_LEN") != null ? Integer.parseInt((String)rowValue.get("FIELD_LEN")) : 0;
		String fieldValue = defaultValue;
		if ("COMMAND".equals(fldName)) {
			fieldValue = params[0];
		}
		if ("PARAMETER".equals(fldName)) {
			fieldValue = params[1];
		}
		if ("BODY_LENGTH".equals(fldName)) {
			fieldValue = BodyLen + "";
		}
		byte[] fieldByte = null;
		if ("HEADER".equals(Type))
		{
			fieldByte = LittleEndianByteHandler.intToByte(Integer.parseInt(fieldValue));
			fldLen = fieldByte.length;
		}
		else
		{
			fieldByte = fieldValue.getBytes();
			if (fldLen == 0) {
				fldLen = fieldByte.length;
			}
		}
		byte[] ret = new byte[fldLen];
		System.arraycopy(fieldByte, 0, ret, 0, fieldByte.length);

		return ret;
	}

	@RequestMapping(value={"/json/{values}"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	public Object getParamJson(@PathVariable String values, ModelMap model)
	{
		System.out.println("################");
		System.out.println(">> " + values.toString());
		System.out.println(">> value of 'v1'" + model.get("v1"));
		System.out.println("################");

		return model.get(values);
	}

	@RequestMapping({"/list"})
	public Object getList()
	{
		return this.mainService.getList(null);
	}

	@RequestMapping(value={"/jsontest/{map}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, headers={"content-type=application/json"}, produces={"application/json"})
	public Object jsonTest(@PathVariable String map)
	{
		System.out.println(">>>>>>>>> " + map);
		return map;
	}

	@RequestMapping({"{p1}/{v1}/{p2}/{v2}/{p3}/{v3}"})
	public String multiMessage(@PathVariable String p1, @PathVariable String v1, @PathVariable String p2, @PathVariable String v2, @PathVariable String p3, @PathVariable String v3)
	{
		return "p1: " + p1 + "v1: " + v1 + "p2: " + p2 + "v2: " + v2 + "p3: " + p3 + "v3: " + v3;
	}

	@RequestMapping({"/param1/{param1}/param2/{param2}"})
	public String message2(@PathVariable String param1, @PathVariable String param2)
	{
		return "param1 : " + param1 + "param2 : " + param2;
	}

	@RequestMapping(value={"/ex/multi"}, params={"param1", "param2", "param3", "param4"})
	public String getBarBySimplePathWithExplicitRequestParams(@RequestParam("param1") String param1, @RequestParam("param2") String param2, @RequestParam("param3") String param3, @RequestParam("param4") String param4)
	{
		return "param1 =" + param1 + " param2=" + param2 + " param3=" + param3 + " param4=" + param4;
	}

	@RequestMapping(value={"/getjson/{val}"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"content-type=application/json"})
	public String getAcceptJson(@RequestBody String val)
	{
		System.out.println("val : " + val);
		return val;
	}

	@RequestMapping({"/if-or-not"})
	public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name)
	{
		return "";
	}
	//  
	//  @RequestMapping({"cti-test"})
	//  public CtiTest getCtiSvrTest()
	//  {
	//    String res = sendSocketServer();
	//    System.out.println(">> res : " + res);
	//    CtiTest ct = new CtiTest("10.1.15.209", 3000, res);
	//    return ct;
	//  }
	//  
	//  private static String sendSocketServer()
	//  {
	//    String res = null;
	//    try
	//    {
	//      socket = new Socket("10.1.9.148", 3000);
	//      os = socket.getOutputStream();
	//      String packet = "AGT_SERV120      P_LOGIN_801 20140703153234 1234                                                                        ";
	//      
	//      Thread.sleep(3000L);
	//      
	//      os.write(packet.getBytes("utf8"));
	//      os.flush();
	//      
	//      is = socket.getInputStream();
	//      
	//      b = new byte[1000];
	//      int num = is.read(b);
	//      
	//      res = new String(b);
	//      System.out.println(">>> RES : \n " + res);
	//      
	//      return res;
	//    }
	//    catch (Exception e)
	//    {
	//      byte[] b;
	//      e.printStackTrace();
	//      return null;
	//    }
	//    finally
	//    {
	//      try
	//      {
	//        is.close();
	//        os.close();
	//        socket.close();
	//      }
	//      catch (Exception e)
	//      {
	//        e.printStackTrace();
	//      }
	//    }
	//  }

	private static boolean ServerAliveCheck(String ip, int port)
	{
		boolean isAlive = false;
		Socket socket = null;
		try
		{
			socket = new Socket(ip, port);
			isAlive = socket.isConnected();
			socket.close();
			return isAlive;
		}
		catch (Exception e) {}
		return false;
	}
}
