package com.court_booking_project.court_booking_server.service.implementations;

import com.court_booking_project.court_booking_server.config.ZaloPayConfig;
import com.court_booking_project.court_booking_server.dto.temp_request.zalopay.ZaloPayCallBackDTO;
import com.court_booking_project.court_booking_server.dto.temp_request.zalopay.ZaloPayRequestCreatePaymentDTO;
import com.court_booking_project.court_booking_server.utils.zalopay.ZaloPayUtils;
import com.court_booking_project.court_booking_server.utils.zalopay.crypto.HMACUtil;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.http.NameValuePair; // https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject; // https://mvnrepository.com/artifact/org.json/jso/
import org.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ZaloPayService {
    ZaloPayConfig zaloPayConfig;
    ZaloPayUtils zaloPayUtils;
    Logger logger = Logger.getLogger(this.getClass().getName());

    public ResponseEntity<?> createPaymentZaloPay(String id, ZaloPayRequestCreatePaymentDTO request) throws Exception
    {
//        int payment_id = new Random().nextInt(1000);
        UUID uuid = UUID.randomUUID();
        String reservation_id = id.toString().substring(0, 30);

        final Map<String,String> embed_data = new HashMap<String,String>() {{
            put("redirecturl", zaloPayConfig.getRedirectUrl());
        }};
        final Map<String, Object>[] items = new Map[]{};

        JSONArray jsonArray = new JSONArray();
        for (Map<String, Object> item : items) {
            JSONObject jsonObject = new JSONObject(item);
            jsonArray.put(jsonObject);
        }

        Map<String, Object> order = new HashMap<String, Object>(){{
            put("app_id", zaloPayConfig.getApp_id());
            put("app_trans_id", zaloPayUtils.getCurrentTimeString("yyMMdd") +"_"+ reservation_id); // translation missing: vi.docs.shared.sample_code.comments.app_trans_id
            put("app_time", System.currentTimeMillis()); // miliseconds
            put("app_user", request.getUserName());
            put("amount", request.getTotalPrice().toString());
            put("description", "Thanh toan don hang #" + reservation_id);
            put("bank_code", "zalopayapp");
            put("callback_url", zaloPayConfig.getCallbackUrl());
            put("item",  jsonArray.toString());
            put("embed_data", new JSONObject(embed_data));
        }};

        // app_id +”|”+ app_trans_id +”|”+ appuser +”|”+ amount +"|" + app_time +”|”+ embed_data +"|" +item
        String data = order.get("app_id") +"|"+ order.get("app_trans_id") +"|"+ order.get("app_user") +"|"+ order.get("amount")
                +"|"+ order.get("app_time") +"|"+ order.get("embed_data") +"|"+ order.get("item");

        System.out.println("hmac_input: " + data);

        order.put("mac", HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, zaloPayConfig.getKey1(), data));

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(zaloPayConfig.getEndpoint());

        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> e : order.entrySet()) {
            params.add(new BasicNameValuePair(e.getKey(), e.getValue().toString()));
        }

        // Content-Type: application/x-www-form-urlencoded
        post.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse res = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        StringBuilder resultJsonStr = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            resultJsonStr.append(line);
        }

        JSONObject result = new JSONObject(resultJsonStr.toString());
        Map<String, Object> resultMap = new HashMap<>();
        for (String key : result.keySet()) {
            System.out.format("%s = %s\n", key, result.get(key));
            resultMap.put(key, result.get(key));
        }

        return ResponseEntity.ok().body(resultMap);
    }

    public String handleZaloCallback(ZaloPayCallBackDTO jsonStr) throws NoSuchAlgorithmException, InvalidKeyException {
        JSONObject result = new JSONObject();
        Mac HmacSHA256 = Mac.getInstance("HmacSHA256");
        HmacSHA256.init(new SecretKeySpec(zaloPayConfig.getKey2().getBytes(), "HmacSHA256"));

        try {
            JSONObject cdata = new JSONObject(jsonStr);
            String dataStr = cdata.getString("data");
            String reqMac = cdata.getString("mac");

            byte[] hashBytes = HmacSHA256.doFinal(dataStr.getBytes());
            String mac = HexFormat.of().formatHex(hashBytes).toLowerCase();

            // kiểm tra callback hợp lệ (đến từ ZaloPay server)
            if (!reqMac.equals(mac)) {
                // callback không hợp lệ
                result.put("return_code", -1);
                result.put("return_message", "mac not equal");
            } else {
                // thanh toán thành công
                // merchant cập nhật trạng thái cho đơn hàng
                JSONObject data = new JSONObject(dataStr);
                logger.info("update order's status = success where app_trans_id = " + data.getString("app_trans_id"));

                result.put("return_code", 1);
                result.put("return_message", "success");
            }
        } catch (Exception ex) {
            result.put("return_code", 0); // ZaloPay server sẽ callback lại (tối đa 3 lần)
            result.put("return_message", ex.getMessage());
        }

        System.out.println(result); // For logging, you might want to return this instead

        return result.toString();// thông báo kết quả cho ZaloPay server
    }
}
