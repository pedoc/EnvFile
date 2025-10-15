package net.ashald.envfile.providers.shenv;


import net.ashald.envfile.EnvVarsProvider;
import net.ashald.envfile.EnvVarsProviderFactory;
import net.ashald.envfile.providers.direnv.DirenvProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ShenvProviderFactory implements EnvVarsProviderFactory {

    @Override
    public EnvVarsProvider createProvider(
            Map<String, String> baseEnvVars,
            Consumer<String> logger
    ) {
        return new ShenvProvider();
    }

    @Override
    public @NotNull String getTitle() {
        return ".sh";
    }

    @Override
    public boolean isEditable() {
        return true;
    }

    @Override
    public @Nullable Predicate<String> getFileNamePredicate() {
        return fileName -> fileName.endsWith(".sh");
    }

    @Override
    public boolean showHiddenFiles() {
        return true;
    }

}
