using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

// Tuve que crear este script para asociarselo a un objeto vac�o debido a que, tal y como ten�amos planteado el c�digo, si el script lo ten�an los orbes,
// si el jugador cog�a el �ltimo y no quedaba ninguno en juego, no se podr�a ejectuar el m�todo Update que actualizaba la puntuaci�n m�xima.
public class PointsController : MonoBehaviour
{
    // Variables est�ticas que almacenan la puntuaci�n actual y m�xima conseguida en una sesi�n de juego.
    private static int score = 0;
    private static int maxScore = 0;
    
    // Textos del panel de victoria, de derrota y el que sale en el HUD.
    public Text pointsText, actualScoreText, maxScoreText, actualScoreWinText, maxScoreWinText;
    
    // Script del player
    private PlayerController playerScript;

    void Start()
    {
        score = 0;
        playerScript = FindObjectOfType<PlayerController>();
    }

   
    void Update()
    {
        
    }

    // Funci�n que sirve para almacenar los puntos que se van obteniendo y actualizarlos tanto en el HUD como en los paneles de victoria y derrota.
    public void SafePoints(int points)
    {
        score += points;
        pointsText.text = "POINTS : " + score.ToString();
        actualScoreText.text = "ACTUAL SCORE:		" + score.ToString();
        actualScoreWinText.text = "ACTUAL SCORE:		" + score.ToString();
    }

    public void UpdateMaxPoints()
    {
        // Si la puntuaci�n actual es mayor que la que est� guardada en el registro de windows en la variable "MaxScore", actualiza el panel de victoria y derrota con dicha puntuaci�n y actualiza
        // la variable del registro de windows con la nueva puntuaci�n. Si no, muestra el contenido de la variable del registro de windows.
        // Recordatorio de ruta de registro : Equipo\HKEY_CURRENT_USER\Software\Unity\UnityEditor\DefaultCompany\Nombre_proyecto
        if (score > PlayerPrefs.GetInt("MaxScore", 0))
        {
            maxScoreText.text = "MAX SCORE:			" + score.ToString();
            maxScoreWinText.text = "MAX SCORE:			" + score.ToString();
            PlayerPrefs.SetInt("MaxScore", score);
        }
        else
        {
            maxScoreText.text = "MAX SCORE:			" + PlayerPrefs.GetInt("MaxScore", 0).ToString();
            maxScoreWinText.text = "MAX SCORE:			" + PlayerPrefs.GetInt("MaxScore", 0).ToString();
            
        }
    }

    public void ResetMaxPoints()
    {
        PlayerPrefs.SetInt("MaxScore", 0);
        actualScoreText.text = "ACTUAL SCORE:		" + 0.ToString();
        actualScoreWinText.text = "ACTUAL SCORE:		" + 0.ToString();
        maxScoreText.text = "MAX SCORE:			" + 0.ToString();
        maxScoreWinText.text = "MAX SCORE:			" + 0.ToString();
    }

}