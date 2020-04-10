# Alert Hook

[![Build Status][1]][2]

Alert Hook is a tool for getting your alerts from alerting services and send them to other places like messengers or etc.

## Supported Services

* **AlertManager** to **BaleMessenger**

    * API

        ````$xslt
            POST - /api/v1/alertmanager/balemessenger/{token}
        ````
        
    * Config

        ```$xslt
            alertmanager {
              balemessenger {
                base-url: "https://tapi.bale.ai"
                api-token: "alerthook"
                bot-token: "alerthook"
                chat-id: 12345
            
                template: """
                  *Status:* {{status}}
                  *Starts At:* {{startsAt}}
                  *Ends At:* {{endsAt}}
                  *Alert Name:* {{labels.alertname}}
                  *Severity:* {{annotations.severity}}
                """
              }
            }
        ```
        
* **AlertManager** to **TelegramMessenger**

    * API

        ````$xslt
            POST - /api/v1/alertmanager/telegrammessenger/{token}
        ````
        
    * Config

        ```$xslt
            alertmanager {
              telegrammessenger {
                base-url: "https://api.telegram.org"
                api-token: "alerthook"
                bot-token: "alerthook"
                chat-id: 12345
            
                template: """
                  **Status:** {{status}}
                  **Starts At:** {{startsAt}}
                  **Ends At:** {{endsAt}}
                  **Alert Name:** {{labels.alertname}}
                  **Severity:** {{annotations.severity}}
                """
              }
            }
        ```
        
* **AlertManager** to **MatrixMessenger**

    * API

        ````$xslt
            POST - /api/v1/alertmanager/matrixmessenger/{token}
        ````
        
    * Config

        ```$xslt
            alertmanager {
                matrixmessenger {
                    base-url: "https://matrix.org"
                    room-id: "room-id"
                    access-token: "access-token"
                    api-token: "alerthook"
                
                    template: """
                      Status: {{status}}
                      Starts At: {{startsAt}}
                      Ends At: {{endsAt}}
                      Alert Name: {{labels.alertname}}
                      Severity: {{annotations.severity}}
                    """
                }
            }
        ```

[1]: https://travis-ci.org/rashadansari/alert-hook.svg?branch=master
[2]: https://travis-ci.org/rashadansari/alert-hook