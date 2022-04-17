package io.github.codeutilities.features;

import io.github.codeutilities.config.enums.QueueMessages;
import io.github.codeutilities.event.EventRegister;
import io.github.codeutilities.event.impl.ChatReceivedEvent;
import io.github.codeutilities.event.listening.EventWatcher;
import io.github.codeutilities.event.listening.IEventListener;
import io.github.codeutilities.loader.Loadable;
import io.github.codeutilities.util.RenderUtil;
import net.minecraft.client.toast.SystemToast;

public class SupportMessages implements Loadable {
    @Override
    public void load() {
        EventRegister.getInstance().registerListener(new SupportMessages.EventListener());
    }

    public static class EventListener implements IEventListener {

        @EventWatcher
        public void onChat(ChatReceivedEvent event) {
            String message = event.getMessage().getString();

            if (message.startsWith("[SUPPORT] ")) {
                message = message.replace("[SUPPORT] ", "");

                if (message.endsWith(" joined the support queue.")) {
                    String player = message.replace(" joined the support queue.", "");

                    if (QueueMessages.TOAST == QueueMessages.getConfig()) {
                        RenderUtil.sendToaster("Support Queue", player + " joined the queue.", SystemToast.Type.NARRATOR_TOGGLE);
                    }

                    event.setCancelled(true);
                }

                if (message.endsWith(" left the support queue.")) {
                    String player = message.replace(" left the support queue.", "");

                    if (QueueMessages.TOAST == QueueMessages.getConfig()) {
                        RenderUtil.sendToaster("Support Queue", player + " left the queue.", SystemToast.Type.NARRATOR_TOGGLE);
                    }

                    event.setCancelled(true);
                }
            }
        }

    }

}
