package abr.mod.photoptics;

import java.util.EnumMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Maps;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PhotopticsKeybindings {
	
	private EnumMap<EnumPhotopticsKeys, KeyBinding> keyMap = Maps.newEnumMap(EnumPhotopticsKeys.class);
	
	public PhotopticsKeybindings() {
		KeyBinding zoomIn = new KeyBinding("key.photoptics.zoomin", Keyboard.KEY_EQUALS, "key.category.photoptics");
		KeyBinding zoomOut = new KeyBinding("key.photoptics.zoomout", Keyboard.KEY_MINUS, "key.category.photoptics");
		KeyBinding observe = new KeyBinding("key.photoptics.observe", Keyboard.KEY_F, "key.category.photoptics");

		keyMap.put(EnumPhotopticsKeys.ZoomIn, zoomIn);
		keyMap.put(EnumPhotopticsKeys.ZoomOut, zoomOut);
		keyMap.put(EnumPhotopticsKeys.Observe, observe);
		
		for(KeyBinding binding : keyMap.values())
			ClientRegistry.registerKeyBinding(binding);
	}
	
	@SubscribeEvent
	public void onKeyInputEvent(KeyInputEvent event) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if(player != null) {
			for(Map.Entry<EnumPhotopticsKeys, KeyBinding> entry : keyMap.entrySet()) {
				if(!entry.getKey().isContinuous() && entry.getValue().isPressed())
				{
					Photoptics.instance.getNetworkHandler().sendKeyInput(entry.getKey());
					PhotopticsTelescopeHandler.onKeyInput(player, entry.getKey());
					return;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onTickEvent(TickEvent.ClientTickEvent event) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if(player != null) {
			for(Map.Entry<EnumPhotopticsKeys, KeyBinding> entry : keyMap.entrySet()) {
				if((entry.getKey().isContinuous() && Keyboard.isKeyDown(entry.getValue().getKeyCode())))
				{
					Photoptics.instance.getNetworkHandler().sendKeyInput(entry.getKey());
					PhotopticsTelescopeHandler.onKeyInput(player, entry.getKey());
					return;
				}
			}
		}
	}

}
