package com.court_booking_project.court_booking_server.controller;

import com.court_booking_project.court_booking_server.config.ZaloPayConfig;
import com.court_booking_project.court_booking_server.dto.ZaloPayCallBackDTO;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.json.JSONObject; // https://mvnrepository.com/artifact/org.json/json
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.logging.Logger;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ZaloPayCallback {

    Logger logger = Logger.getLogger(this.getClass().getName());
    ZaloPayConfig zaloPayConfig;

    @PostMapping("/callback")
    public String callback(@RequestBody ZaloPayCallBackDTO jsonStr) throws NoSuchAlgorithmException, InvalidKeyException {
        JSONObject result = new JSONObject();
        System.out.println("Callback....");
        Mac HmacSHA256 = Mac.getInstance("HmacSHA256");
        HmacSHA256.init(new SecretKeySpec(zaloPayConfig.getKey2().getBytes(), "HmacSHA256"));

        try {
            JSONObject cbdata = new JSONObject(jsonStr);
            String dataStr = cbdata.getString("data");
            String reqMac = cbdata.getString("mac");

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

        // thông báo kết quả cho ZaloPay server
        return result.toString();
    }
}
