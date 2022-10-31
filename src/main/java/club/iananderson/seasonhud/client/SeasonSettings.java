//package club.iananderson.seasonhud.client;
//
//import xaero.common.IXaeroMinimap;
//import xaero.common.settings.ModOptions;
//import xaero.common.settings.ModSettings;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//
//public class SeasonSettings extends ModSettings{
//
//    public SeasonSettings(IXaeroMinimap modMain) throws IOException {
//        super(modMain);
//    }
//    public boolean displayCurrentSeason = true;
//
//    public void writeSettings(PrintWriter writer) {
//        writer.println("displayCurrentSeason:" + this.displayCurrentSeason);
//    }
//    public void readSetting(String[] args) {
//        String valueString = args.length < 2 ? "" : args[1];
//        if (args[0].equalsIgnoreCase("ignoreUpdate")) {
//            ModSettings.ignoreUpdate = Integer.parseInt(valueString);
//        }
//        else {
//            if (args[0].equalsIgnoreCase("adjustHeightForCarpetLikeBlocks")) {
//                adjustHeightForCarpetLikeBlocks = valueString.equals("true");
//            }
//            else if (args[0].equalsIgnoreCase("displayCurrentClaim")) {
//                this.displayCurrentSeason = valueString.equals("true");
//            }
//        }
//    }
//    public boolean getClientBooleanValue(ModOptions o) {
//        if (o.isIngameOnly() && !canEditIngameSettings()) {
//            return false;
//        }else {
//            return o == ModOptions.PAC_CURRENT_CLAIM ? this.displayCurrentSeason : false;
//        }
//    }
//}
//
