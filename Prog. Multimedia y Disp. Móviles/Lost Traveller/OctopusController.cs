using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class OctopusController : MonoBehaviour
{

    public Transform target;
    public bool isHurt = false;
    private float speed = 4f;
    private Vector3 start, end;
    private SpriteRenderer spriteRenderer;

    // Start is called before the first frame update
    void Start()
    {
        start = transform.position;
        end = target.position;
        spriteRenderer = GetComponent<SpriteRenderer>();
    }

    // Update is called once per frame
    void Update()
    {
        transform.position = Vector3.MoveTowards(transform.position, target.position, speed * Time.deltaTime);

        if (transform.position == target.position)
        {

            if (target.position == end)
            {
                target.position = start;
                spriteRenderer.flipX = true;
            }
            else
            {
                target.position = end;
                spriteRenderer.flipX = false;

            }
        }
    }
}

