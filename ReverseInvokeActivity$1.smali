.class Lcom/example/mac1k3h4/hellowandroid/MainActivity$1;
.super Ljava/lang/Object;
.source "MainActivity.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/example/mac1k3h4/hellowandroid/MainActivity;->onCreate(Landroid/os/Bundle;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/example/mac1k3h4/hellowandroid/MainActivity;


# direct methods
.method constructor <init>(Lcom/example/mac1k3h4/hellowandroid/MainActivity;)V
    .locals 0
    .param p1, "this$0"    # Lcom/example/mac1k3h4/hellowandroid/MainActivity;

    .prologue
    .line 29
    iput-object p1, p0, Lcom/example/mac1k3h4/hellowandroid/MainActivity$1;->this$0:Lcom/example/mac1k3h4/hellowandroid/MainActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 6
    .param p1, "v"    # Landroid/view/View;

    .prologue
    .line 32
    new-instance v2, Landroid/content/Intent;

    invoke-direct {v2}, Landroid/content/Intent;-><init>()V

    .line 34
    .local v2, "it":Landroid/content/Intent;
    iget-object v4, p0, Lcom/example/mac1k3h4/hellowandroid/MainActivity$1;->this$0:Lcom/example/mac1k3h4/hellowandroid/MainActivity;

    invoke-virtual {v4}, Lcom/example/mac1k3h4/hellowandroid/MainActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v4

    const v5, 0x7f060014

    invoke-virtual {v4, v5}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v3

    .line 36
    .local v3, "pac":Ljava/lang/String;
    iget-object v4, p0, Lcom/example/mac1k3h4/hellowandroid/MainActivity$1;->this$0:Lcom/example/mac1k3h4/hellowandroid/MainActivity;

    invoke-virtual {v4}, Lcom/example/mac1k3h4/hellowandroid/MainActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v4

    const v5, 0x7f060012

    invoke-virtual {v4, v5}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v0

    .line 37
    .local v0, "cls":Ljava/lang/String;
    new-instance v1, Landroid/content/ComponentName;

    invoke-direct {v1, v3, v0}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    .line 38
    .local v1, "component":Landroid/content/ComponentName;
    const-string v4, "android.intent.action.VIEW"

    invoke-virtual {v2, v4}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    .line 39
    const-string v4, "ext"

    const/16 v5, 0x64

    invoke-virtual {v2, v4, v5}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    .line 40
    invoke-virtual {v2, v1}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    .line 41
    iget-object v4, p0, Lcom/example/mac1k3h4/hellowandroid/MainActivity$1;->this$0:Lcom/example/mac1k3h4/hellowandroid/MainActivity;

    invoke-virtual {v4, v2}, Lcom/example/mac1k3h4/hellowandroid/MainActivity;->startActivity(Landroid/content/Intent;)V

    .line 42
    return-void
.end method
