package com.liuyanzhao.sens.user.web.controller;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.liuyanzhao.sens.common.vo.Response;
import com.liuyanzhao.sens.user.api.entity.Setting;
import com.liuyanzhao.sens.user.api.service.SettingService;
import com.liuyanzhao.sens.user.web.constant.SettingConstant;
import com.liuyanzhao.sens.user.web.util.CommonUtil;
import com.liuyanzhao.sens.user.web.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 基本配置接口
 *
 * @author liuyanzhao
 */
@Slf4j
@RestController
@RequestMapping("/setting")
public class SettingController {

    @Autowired
    private SettingService settingService;

    /**
     * 查看私密配置
     *
     * @param settingName
     * @return
     */
    @RequestMapping(value = "/seeSecret/{settingName}", method = RequestMethod.GET)
    public Response<Object> seeSecret(@PathVariable String settingName) {

        String result = "";
        Setting setting = settingService.findById(settingName);
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            return Response.no("配置不存在");
        }
        if (settingName.equals(SettingConstant.QINIU_OSS) || settingName.equals(SettingConstant.ALI_OSS)
                || settingName.equals(SettingConstant.TENCENT_OSS) || settingName.equals(SettingConstant.MINIO_OSS)) {

            result = new Gson().fromJson(setting.getValue(), OssSetting.class).getSecretKey();
        } else if (settingName.equals(SettingConstant.ALI_SMS)) {

            result = new Gson().fromJson(setting.getValue(), SmsSetting.class).getSecretKey();
        } else if (settingName.equals(SettingConstant.EMAIL_SETTING)) {

            result = new Gson().fromJson(setting.getValue(), EmailSetting.class).getPassword();
        } else if (settingName.equals(SettingConstant.VAPTCHA_SETTING)) {

            result = new Gson().fromJson(setting.getValue(), VaptchaSetting.class).getSecretKey();
        }
        return Response.yes(result);
    }

    /**
     * 检查OSS配置
     *
     * @return
     */
    @RequestMapping(value = "/oss/check", method = RequestMethod.GET)
    public Response<Object> osscheck() {

        Setting setting = settingService.findById(SettingConstant.OSS_USED);
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            return Response.no(501, "您还未配置第三方OSS服务");
        }
        return Response.yes(setting.getValue());
    }

    /**
     * 查看OSS配置
     *
     * @param serviceName
     * @return
     */
    @RequestMapping(value = "/oss/{serviceName}", method = RequestMethod.GET)
    public Response<OssSetting> oss(@PathVariable String serviceName) {

        Setting setting = new Setting();
        if (serviceName.equals(SettingConstant.QINIU_OSS) || serviceName.equals(SettingConstant.ALI_OSS)
                || serviceName.equals(SettingConstant.TENCENT_OSS) || serviceName.equals(SettingConstant.MINIO_OSS)
                || serviceName.equals(SettingConstant.LOCAL_OSS)) {
            setting = settingService.findById(serviceName);
        }
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            return Response.yes();
        }
        OssSetting ossSetting = new Gson().fromJson(setting.getValue(), OssSetting.class);
        ossSetting.setSecretKey("**********");
        return Response.yes(ossSetting);
    }

    /**
     * 查看短信配置
     *
     * @param serviceName
     * @return
     */
    @RequestMapping(value = "/sms/{serviceName}", method = RequestMethod.GET)
    public Response<SmsSetting> sms(@PathVariable String serviceName) {

        Setting setting = new Setting();
        if (serviceName.equals(SettingConstant.ALI_SMS)) {
            setting = settingService.findById(SettingConstant.ALI_SMS);
        }
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            return Response.yes();
        }
        SmsSetting smsSetting = new Gson().fromJson(setting.getValue(), SmsSetting.class);
        smsSetting.setSecretKey("**********");
        if (smsSetting.getType() != null) {
            Setting code = settingService.findById(CommonUtil.getSmsTemplate(smsSetting.getType()));
            smsSetting.setTemplateCode(code.getValue());
        }
        return Response.yes(smsSetting);
    }

    /**
     * 查看短信模板配置
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "/sms/templateCode/{type}", method = RequestMethod.GET)
    public Response<String> smsTemplateCode(@PathVariable Integer type) {

        String templateCode = "";
        if (type != null) {
            String template = CommonUtil.getSmsTemplate(type);
            Setting setting = settingService.findById(template);
            if (StrUtil.isNotBlank(setting.getValue())) {
                templateCode = setting.getValue();
            }
        }
        return Response.yes(templateCode);
    }

    /**
     * 查看vaptcha配置
     *
     * @return
     */
    @RequestMapping(value = "/vaptcha", method = RequestMethod.GET)
    public Response<VaptchaSetting> vaptcha() {

        Setting setting = settingService.findById(SettingConstant.VAPTCHA_SETTING);
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            return Response.no();
        }
        VaptchaSetting vaptchaSetting = new Gson().fromJson(setting.getValue(), VaptchaSetting.class);
        vaptchaSetting.setSecretKey("**********");
        return Response.yes(vaptchaSetting);
    }

    /**
     * 查看email配置
     *
     * @return
     */
    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public Response<EmailSetting> email() {

        Setting setting = settingService.findById(SettingConstant.EMAIL_SETTING);
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            return Response.no();
        }
        EmailSetting emailSetting = new Gson().fromJson(setting.getValue(), EmailSetting.class);
        emailSetting.setPassword("**********");
        return Response.yes(emailSetting);
    }

    /**
     * 查看其他配置
     * @return
     */
    @RequestMapping(value = "/other", method = RequestMethod.GET)
    public Response<OtherSetting> other() {

        Setting setting = settingService.findById(SettingConstant.OTHER_SETTING);
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            return Response.no();
        }
        OtherSetting otherSetting = new Gson().fromJson(setting.getValue(), OtherSetting.class);
        return Response.yes(otherSetting);
    }

    /**
     * OSS配置
     * @param ossSetting
     * @return
     */
    @RequestMapping(value = "/oss/set", method = RequestMethod.POST)
    public Response<Object> ossSet(@ModelAttribute OssSetting ossSetting) {

        String name = ossSetting.getServiceName();
        Setting setting = settingService.findById(name);
        if (name.equals(SettingConstant.QINIU_OSS) || name.equals(SettingConstant.ALI_OSS)
                || name.equals(SettingConstant.TENCENT_OSS) || name.equals(SettingConstant.MINIO_OSS)) {

            // 判断是否修改secrectKey 保留原secrectKey 避免保存***加密字符
            if (StrUtil.isNotBlank(setting.getValue()) && !ossSetting.getChanged()) {
                String secrectKey = new Gson().fromJson(setting.getValue(), OssSetting.class).getSecretKey();
                ossSetting.setSecretKey(secrectKey);
            }
        }
        setting.setValue(new Gson().toJson(ossSetting));
        settingService.saveOrUpdate(setting);
        Setting used = settingService.findById(SettingConstant.OSS_USED);
        used.setValue(name);
        settingService.saveOrUpdate(used);
        return Response.yes();
    }

    /**
     * 短信配置
     * @param smsSetting
     * @return
     */
    @RequestMapping(value = "/sms/set", method = RequestMethod.POST)
    public Response<Object> smsSet(@ModelAttribute SmsSetting smsSetting) {

        if (smsSetting.getServiceName().equals(SettingConstant.ALI_SMS)) {
            // 阿里
            Setting setting = settingService.findById(SettingConstant.ALI_SMS);
            if (StrUtil.isNotBlank(setting.getValue()) && !smsSetting.getChanged()) {
                String secrectKey = new Gson().fromJson(setting.getValue(), SmsSetting.class).getSecretKey();
                smsSetting.setSecretKey(secrectKey);
            }
            if (smsSetting.getType() != null) {
                Setting codeSetting = settingService.findById(CommonUtil.getSmsTemplate(smsSetting.getType()));
                codeSetting.setValue(smsSetting.getTemplateCode());
                settingService.saveOrUpdate(codeSetting);
            }
            smsSetting.setType(null);
            smsSetting.setTemplateCode(null);
            setting.setValue(new Gson().toJson(smsSetting));
            settingService.saveOrUpdate(setting);

            Setting used = settingService.findById(SettingConstant.SMS_USED);
            used.setValue(SettingConstant.ALI_SMS);
            settingService.saveOrUpdate(used);
        }
        return Response.yes();
    }

    /**
     * email配置
     * @param emailSetting
     * @return
     */
    @RequestMapping(value = "/email/set", method = RequestMethod.POST)
    public Response<Object> emailSet(@ModelAttribute EmailSetting emailSetting) {

        Setting setting = settingService.findById(SettingConstant.EMAIL_SETTING);
        if (StrUtil.isNotBlank(setting.getValue()) && !emailSetting.getChanged()) {
            String password = new Gson().fromJson(setting.getValue(), EmailSetting.class).getPassword();
            emailSetting.setPassword(password);
        }
        setting.setValue(new Gson().toJson(emailSetting));
        settingService.saveOrUpdate(setting);
        return Response.yes();
    }

    /**
     * vaptcha配置
     * @param vaptchaSetting
     * @return
     */
    @RequestMapping(value = "/vaptcha/set", method = RequestMethod.POST)
    public Response<Object> vaptchaSet(@ModelAttribute VaptchaSetting vaptchaSetting) {

        Setting setting = settingService.findById(SettingConstant.VAPTCHA_SETTING);
        if (StrUtil.isNotBlank(setting.getValue()) && !vaptchaSetting.getChanged()) {
            String key = new Gson().fromJson(setting.getValue(), VaptchaSetting.class).getSecretKey();
            vaptchaSetting.setSecretKey(key);
        }
        setting.setValue(new Gson().toJson(vaptchaSetting));
        settingService.saveOrUpdate(setting);
        return Response.yes();
    }

    /**
     * 其他配置
     * @param otherSetting
     * @return
     */
    @RequestMapping(value = "/other/set", method = RequestMethod.POST)
    public Response<Object> otherSet(@ModelAttribute OtherSetting otherSetting) {

        Setting setting = settingService.findById(SettingConstant.OTHER_SETTING);
        setting.setValue(new Gson().toJson(otherSetting));
        settingService.saveOrUpdate(setting);
        return Response.yes();
    }
}
