package com.dyy.tsp.evgb.gateway.exe.handler;

import com.alibaba.fastjson.JSONObject;
import com.dyy.tsp.common.util.ByteUtil;
import com.dyy.tsp.evgb.gateway.exe.event.IAction;
import com.dyy.tsp.evgb.gateway.exe.event.IFocus;
import com.dyy.tsp.evgb.gateway.exe.gui.HexVisualizationPanel;
import com.dyy.tsp.evgb.gateway.exe.gui.SimulationHexPanel;
import com.dyy.tsp.evgb.gateway.protocol.entity.*;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.CommandType;
import com.dyy.tsp.netty.common.IStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Data
public class BusinessHandler implements IAction, IFocus {

    private static Map<CommandType,IStatus> bodyClassMap = new ConcurrentHashMap<CommandType,IStatus>(6){{
        put(CommandType.PLATFORM_LOGIN, new PlatformLogin());
        put(CommandType.PLATFORM_LOGOUT, new PlatformLogout());
        put(CommandType.VEHICLE_LOGIN, new VehicleLogin());
        put(CommandType.VEHICLE_LOGOUT, new VehicleLogout());
        put(CommandType.REALTIME_DATA_REPORTING, new RealTimeData());
        put(CommandType.REPLACEMENT_DATA_REPORTING, new RealTimeData());
    }};

    private static String HEX_FORMAT = "^[A-Fa-f0-9]+$";

    private static String VIN_FORMAT = "^[A-HJ-NPR-Z\\d]{17}$";

    private HexVisualizationPanel hexVisualizationPanel;

    private SimulationHexPanel simulationHexPanel;

    private EvGBProtocol producer = new EvGBProtocol();

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command){
            case "HEX-PARSE":
                String hex = hexVisualizationPanel.getInputArea().getText();
                if(StringUtils.isBlank(hex)){
                    hexVisualizationPanel.showMessage("请输入正确格式的HEX字符串",Boolean.TRUE);
                    return;
                }
                if(!hex.matches(HEX_FORMAT)){
                    hexVisualizationPanel.showMessage("请输入正确格式的HEX字符串",Boolean.TRUE);
                    return;
                }
                log.debug("PARSE HEX [{}]",hex);
                hex = hex.replaceAll(" ","");
                ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
                buffer.writeBytes(ByteUtil.hexStringToBytes(hex));
                EvGBProtocol protocol = null;
                try{
                    protocol = producer.decode(buffer);
                }catch (IndexOutOfBoundsException e1){
                    hexVisualizationPanel.showMessage("请检查报文结构",Boolean.TRUE);
                    e1.printStackTrace();
                    return;
                }
                CommandType commandType = protocol.getCommandType();
                IStatus iStatus = bodyClassMap.get(commandType);
                DataBody body = protocol.getBody();
                if(iStatus!=null && body!=null && body.getByteBuf()!=null){
                    try{
                        body.setJson((JSONObject) JSONObject.toJSON(iStatus.decode(protocol.getBody().getByteBuf())));
                        protocol.setBody(body);
                    }catch (IndexOutOfBoundsException e1){
                        e1.printStackTrace();
                        hexVisualizationPanel.showMessage("请检查报文数据单元",Boolean.TRUE);
                        return;
                    }
                }
                String message = JSONObject.toJSONString(protocol);
                log.debug("JSON [{}]",message);
                hexVisualizationPanel.showMessage(message,Boolean.FALSE);
                break;
            case "HEX-CLEAR":
                hexVisualizationPanel.clear();
                break;
            case "JSON-DECODE":
                String json = simulationHexPanel.getInputArea().getText();
                if(StringUtils.isBlank(json)){
                    simulationHexPanel.showMessage("请输入正确格式的JSON字符串",Boolean.TRUE);
                    return;
                }
                EvGBProtocol evGBProtocol = null;
                try {
                    evGBProtocol = JSONObject.parseObject(json, EvGBProtocol.class);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    simulationHexPanel.showMessage("请输入正确格式的JSON字符串",Boolean.TRUE);
                    return;
                }
                log.debug("JSON DECODE [{}]",json);
                if(StringUtils.isBlank(evGBProtocol.getVin())){
                    simulationHexPanel.showMessage("VIN码不能为空",Boolean.TRUE);
                    return;
                }else{
                    if(!evGBProtocol.getVin().matches(VIN_FORMAT)){
                        simulationHexPanel.showMessage("VIN码格式错误",Boolean.TRUE);
                        return;
                    }
                }
                if(evGBProtocol.getCommandType() == null){
                    simulationHexPanel.showMessage("指令类型不能为空",Boolean.TRUE);
                    return;
                }
                DataBody dataBody = evGBProtocol.getBody();
                if(dataBody!=null){
                    if(dataBody.getJson()!=null){
                        try{
                            IStatus status = dataBody.getJson().toJavaObject(bodyClassMap.get(evGBProtocol.getCommandType()).getClass());
                            dataBody.setByteBuf(status.encode());
                            evGBProtocol.setBody(dataBody);
                        }catch (Exception e1){
                            e1.printStackTrace();
                            simulationHexPanel.showMessage("请检查数据单元",Boolean.TRUE);
                            return;
                        }
                    }
                }
                ByteBuf encode = evGBProtocol.encode();
                byte[] encodeArray = new byte[encode.writerIndex()];
                encode.readBytes(encodeArray);
                String encodeHex = ByteUtil.byteToHex(encodeArray);
                encode.release();
                log.debug("HEX [{}]",encodeHex);
                simulationHexPanel.showMessage(encodeHex,Boolean.FALSE);
                break;
            case "JSON-CLEAR":
                simulationHexPanel.clear();
                break;
            default:
                break;
        }
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}
