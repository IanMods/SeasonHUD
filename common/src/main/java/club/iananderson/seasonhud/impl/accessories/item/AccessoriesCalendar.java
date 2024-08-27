package club.iananderson.seasonhud.impl.accessories.item;

import club.iananderson.seasonhud.platform.Services;
import com.mojang.blaze3d.vertex.PoseStack;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.client.SimpleAccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class AccessoriesCalendar implements Accessory {
  public AccessoriesCalendar() {
  }

  public static void clientInit() {
    AccessoriesRendererRegistry.registerRenderer(Services.SEASON.calendar(), Renderer::new);
  }

  public static void init() {
    AccessoriesAPI.registerAccessory(Services.SEASON.calendar(), new AccessoriesCalendar());
  }

  public static class Renderer implements SimpleAccessoryRenderer {

    @Override
    public <M extends LivingEntity> void align(ItemStack stack, SlotReference reference, EntityModel<M> model,
        PoseStack matrices) {
      if (!(model instanceof HumanoidModel<? extends LivingEntity> humanoidModel)) {
        return;
      }

      matrices.scale(0.4F, 0.4F, 0.4F);
      AccessoryRenderer.transformToModelPart(matrices, humanoidModel.body, 0.75, -1, null);
      matrices.translate(-0.25F, -1.75F, -0.72F);
    }
  }
}