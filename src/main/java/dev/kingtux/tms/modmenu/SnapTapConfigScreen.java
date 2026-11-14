package dev.kingtux.tms.modmenu;

import dev.kingtux.tms.api.config.SnapTapSettings;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class SnapTapConfigScreen extends Screen {
    private final Screen parent;

    public SnapTapConfigScreen(Screen parent) {
        super(Text.literal("SnapTap"));
        this.parent = parent;
    }

    private static Text label() {
        return Text.literal("\"+\" button: " + SnapTapSettings.INSTANCE.getPlusMode().getLabel());
    }

    @Override
    protected void init() {
        this.addDrawableChild(ButtonWidget.builder(label(), button -> {
            SnapTapSettings.INSTANCE.setPlusMode(SnapTapSettings.INSTANCE.getPlusMode().next());
            button.setMessage(label());
        }).dimensions(this.width / 2 - 100, this.height / 2 - 24, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> this.close())
                .dimensions(this.width / 2 - 100, this.height / 2 + 8, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 30, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer,
                Text.literal("Always = every row, On hover = only the row you point at, Hidden = never"),
                this.width / 2, this.height / 2 + 40, 0xFFA0A0A0);
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }
}
